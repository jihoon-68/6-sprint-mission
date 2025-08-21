package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    public Message createMessage(String senderName, String message);
    public Message findMessageById(UUID id);
    public List<Message> findAllMessages();
    public Message updateMessage(Message message);
    public void deleteMessage(UUID id);

}
