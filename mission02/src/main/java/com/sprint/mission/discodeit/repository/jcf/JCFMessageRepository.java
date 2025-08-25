package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.Exception.NotFoundException;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    private final List<Message> messages;

    public JCFMessageRepository() {messages = new ArrayList<>();}


    @Override
    public void save(Message message) {
        messages.add(message);
    }

    @Override
    public void remove(Message message) {
        messages.remove(message);
    }

    @Override
    public List<Message> findAll() {
        if(messages.isEmpty()) {
            return List.of();
        } else {
            return new ArrayList<>(messages);
        }
    }

    @Override
    public List<Message> findByReceiverId(UUID receiverId) {
        return messages.stream()
                .filter(message -> message.getReceiver().equals(receiverId))
                .toList();
    }

    @Override
    public Optional<Message> findById(UUID messageId) throws NotFoundException {
        return messages.stream()
                .filter(message -> message.getUuid().equals(messageId))
                .findFirst();
    }

    @Override
    public boolean existsById(UUID messageId) {
        return messages.stream().anyMatch(message -> message.getUuid().equals(messageId));
    }
}
