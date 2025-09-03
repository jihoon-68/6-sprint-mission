package com.sprint.mission.discodeit.serviece;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageServiece {
  void addMessage(Message message, UUID userId, UUID channelId);
  Message readMessage(UUID messageId);
  void updateMessage(Message message);
  void deleteMessage(UUID messageId);
  List<Message> readAllMessage();
}
