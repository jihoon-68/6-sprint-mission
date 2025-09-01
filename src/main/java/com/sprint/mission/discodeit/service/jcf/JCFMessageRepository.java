package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> data = new HashMap<>();

    @Override
    public Message create(Message message) {
        data.put(message.getmessageId(), message);
        return message;
    }

    @Override
    public Message read(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Message update(UUID id, Message message) {
        if (data.containsKey(id)) {
            data.put(id, message);
            return message;
        }
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}
