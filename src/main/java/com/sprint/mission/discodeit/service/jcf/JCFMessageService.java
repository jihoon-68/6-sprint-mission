package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data = new HashMap<>();

    @Override
    public void createMessage(String msg) {
        Message message = new Message(msg);
        data.put(message.getId(), message);
    }

    @Override
    public Message getMessage(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateMessage(UUID id, String msg) {
        Message message = data.get(id);
        if (message != null) {
            message.update(msg);
        }
    }

    @Override
    public void deleteMessage(UUID id) {
        data.remove(id);
    }
}
