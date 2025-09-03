package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    void addMessage(Message message, UUID userId, UUID channelId);
    Message readMessage(UUID messageId);
    void deleteMessage(UUID messageId);
    List<Message> readAllMessage();
    void updateMessage(Message message);
}
