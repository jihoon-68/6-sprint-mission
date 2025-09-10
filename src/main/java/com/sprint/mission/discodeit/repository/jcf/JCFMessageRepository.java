package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> messageMap;

    public JCFMessageRepository() {
        this.messageMap = new HashMap<>();
    }

    @Override
    public Message save(Message message) {
        this.messageMap.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(this.messageMap.get(id));
    }

    @Override
    public List<Message> findAll() {
        return this.messageMap.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.messageMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.messageMap.remove(id);
    }
}
