package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {

    private Map<UUID, Message> store = new HashMap<>();

    @Override
    public Message save(Message message) {
        store.put(message.getId(), message);
        return message;
    }

    @Override
    public Message findById(UUID id) {
        return store.get(id);
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(UUID id) {
        if (existsById(id)) {
            store.remove(id);
            return true;
        } else {
            System.out.println("실패");
            return false;
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }
}
