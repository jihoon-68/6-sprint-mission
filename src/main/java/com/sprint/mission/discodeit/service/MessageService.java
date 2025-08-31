package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    Message create(Message message);
    Message read(UUID id);
    List<Message> readAll();
    Message update(UUID id, Message updatedMessage);
    boolean delete(UUID id);
}
