package com.sprint.mission.discodeit.message.repository;

import com.sprint.mission.discodeit.common.exception.DiscodeitException.DiscodeitPersistenceException;
import com.sprint.mission.discodeit.common.persistence.Validator;
import com.sprint.mission.discodeit.message.domain.Message;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractMessageRepository implements MessageRepository {

    protected AbstractMessageRepository() {
    }

    @Override
    public Message save(Message message) {
        Validator<Message> validator = Validator.identity();
        if (message.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    Message::getId,
                    messageKey -> new DiscodeitPersistenceException(
                            "Message ID already exists: '%s'".formatted(messageKey.getId()))
            ));
        }
        Map<UUID, Message> data = getData();
        Message validated = validator.validate(data, message);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Message findById(UUID id) {
        Map<UUID, Message> data = getData();
        Message message = data.get(id);
        if (message == null) {
            throw new DiscodeitPersistenceException("Message not found for ID: '%s'".formatted(id));
        }
        return message;
    }

    @Override
    public Instant maxCreatedAtByChannelId(UUID channelId) {
        Map<UUID, Message> data = getData();
        Map<UUID, Set<Message>> channelIdToMessages = groupByChannelId(data);
        Set<Message> messages = channelIdToMessages.get(channelId);
        if (messages == null) {
            throw new DiscodeitPersistenceException("Channel not found for ID: '%s'".formatted(channelId));
        }
        return messages.stream()
                .max(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getCreatedAt)
                .orElse(Instant.MIN);
    }

    @Override
    public Iterable<Message> findAll() {
        Map<UUID, Message> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public Iterable<Message> findAllByChannelId(UUID channelId) {
        Map<UUID, Message> data = getData();
        Map<UUID, Set<Message>> channelIdToMessages = groupByChannelId(data);
        return channelIdToMessages.get(channelId);
    }

    @Override
    public void deleteById(UUID id) {
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
