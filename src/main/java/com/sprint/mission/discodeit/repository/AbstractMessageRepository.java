package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.MESSAGE_ID_ALREADY_EXISTS;

public abstract class AbstractMessageRepository implements MessageRepository {

    protected AbstractMessageRepository() {
    }

    @Override
    public Message save(Message message) {
        Validator<Message> validator = Validator.identity();
        if (message.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    Message::getId,
                    () -> new IllegalStateException(MESSAGE_ID_ALREADY_EXISTS.formatted(message.getId()))
            ));
        }
        Map<UUID, Message> data = getData();
        Message validated = validator.validate(data, message);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Optional<Message> find(UUID id) {
        Map<UUID, Message> data = getData();
        Message message = data.get(id);
        return Optional.ofNullable(message);
    }

    @Override
    public Optional<Instant> findLastCreatedAt(UUID channelId) {
        Map<UUID, Message> data = getData();
        Map<UUID, Set<Message>> channelIdToMessages = groupByChannelId(data);
        Set<Message> messages = channelIdToMessages.get(channelId);
        return messages.stream()
                .max(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getCreatedAt);
    }

    @Override
    public Set<Message> findAll() {
        Map<UUID, Message> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public Set<Message> findAll(UUID channelId) {
        Map<UUID, Message> data = getData();
        Map<UUID, Set<Message>> channelIdToMessages = groupByChannelId(data);
        return channelIdToMessages.get(channelId);
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, Message> data = getData();
        data.remove(id);
        flush(data);
    }

    private Map<UUID, Set<Message>> groupByChannelId(Map<UUID, Message> data) {
        return data.values()
                .stream()
                .collect(Collectors.groupingBy(Message::getChannelId, Collectors.toSet()));
    }

    protected abstract Map<UUID, Message> getData();

    protected void flush(Map<?, ?> data) {
    }
}
