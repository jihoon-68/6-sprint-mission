package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> messages;

    public JCFMessageRepository() {
        this.messages = new HashMap<UUID, Message>();
    }

    @Override
    public void save(Message message) {
        messages.put(message.getId(), message);
    }

    @Override
    public Map<UUID, Message> getMessages() {
        return messages.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean delete(Message message) {
        if(messages.containsKey(message.getId()) == false)
        {
            System.out.println("message does not exsist. deleteMessage");
            return false;
        }

        messages.remove(message.getId());
        return true;
    }

    @Override
    public boolean update(Message message) {
        if(messages.containsKey(message.getId()) == false)
        {
            System.out.println("message does not exsist. updateMessage");
            return false;
        }

        Message temp = messages.get(message.getId());
        temp.updateUpdatedAt();
        temp.updateMessage(message.getMessage());
        messages.put(message.getId(),temp);
        return false;
    }

    @Override
    public void deleteAll() {
        messages.clear();
    }
}
