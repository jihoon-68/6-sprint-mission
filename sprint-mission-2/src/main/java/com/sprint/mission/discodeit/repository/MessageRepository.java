package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    public void createMessage(Message message);
    public Message findMessageById(UUID id);
    public List<Message> findAllMessages();
    public void updateMessage(Message message);
    public void deleteMessage(UUID id);

}
