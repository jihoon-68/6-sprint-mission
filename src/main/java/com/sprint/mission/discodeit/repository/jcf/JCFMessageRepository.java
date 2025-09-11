package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    public final List<Message> MessageDate;

    public JCFMessageRepository() {
        MessageDate = new ArrayList<Message>();
    }

    @Override
    public Message save(Message message) {
        int idx = MessageDate.indexOf(message);
        if (idx >=0) {
            MessageDate.set(idx, message);
        }else {
            MessageDate.add(message);
        }
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {

        return MessageDate.stream()
                .filter(message -> message.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Message> findAll() {

        return List.copyOf(MessageDate);
    }

    @Override
    public boolean existsById(UUID id) {

        return MessageDate.stream()
                .anyMatch(message -> message.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {

        MessageDate.removeIf(message -> message.getId().equals(id));
    }
}
