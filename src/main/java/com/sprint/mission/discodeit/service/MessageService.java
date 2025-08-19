package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    void createMessage(String message);
    Message getMessage(UUID id);
    List<Message> getMessages();
    void updateMessage(UUID id, String message);
    void deleteMessage(UUID id);
}
