package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final Map<UUID, Message> data;

    public JCFMessageRepository(ChannelRepository channelRepository, UserRepository userRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.data = new HashMap<>();
    }

    @Override
    public Message save(Message message) {
        channelRepository.find(message.channelId()).orElseThrow();
        userRepository.find(message.authorId()).orElseThrow();
        data.put(message.id(), message);
        return message;
    }

    @Override
    public Optional<Message> find(UUID id) {
        Message message = data.get(id);
        return Optional.ofNullable(message);
    }

    @Override
    public Set<Message> findAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
