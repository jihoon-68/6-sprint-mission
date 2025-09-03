package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {
      Message createMessage(User sender, String message);
      Message findMessageById(UUID id);
      List<Message> findAllMessages();
      void updateMessage(Message message);
      void deleteMessage(UUID id);
}
