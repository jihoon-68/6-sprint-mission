package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    public void createMessage(User sender, String message);
    public Message findMessageById(UUID id);
    public List<Message> findAllMessages();
    public Message updateMessage(Message message);
    public void deleteMessage(UUID id);
}
