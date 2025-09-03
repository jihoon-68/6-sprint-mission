package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    void createMessage(Message message);
    Message findMessageById(UUID id);
    List<Message> findAllMessages();
    void updateMessage(Message message);
    void deleteMessage(UUID id);

}
