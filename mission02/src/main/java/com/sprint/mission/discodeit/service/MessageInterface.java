package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageInterface {

    void send(String messageContext, UUID senderId, UUID receiverId);

    void changeContext(UUID messageId, String newMessageContext);

    void delete(UUID messageId);

    String getMessageById(UUID messageId);

    List<Message> getMessagesByReceiver(UUID receiverId);

    List<Message> getAllMessages();
}
