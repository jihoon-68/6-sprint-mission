package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {


    Message createMessage(UUID senderId, UUID receiverId, String contents);

    Message getMessageById(UUID id);

    List<Message> getAllMessages();

    Message updateMessage(UUID id, String newContents);

    boolean deleteMessage(UUID id);
}

