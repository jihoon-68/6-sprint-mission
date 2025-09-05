package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(UUID channel, UUID user, String content);
    Message find(UUID id);
    List<Message> findAll();
    void update(Message message);
    void delete(UUID id);
}
