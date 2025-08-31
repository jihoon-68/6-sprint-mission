package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    Message create(Message message);
    Message read(UUID id);
    List<Message> readAll();
    Message update(UUID id, Message message);
    boolean delete(UUID id);
}
