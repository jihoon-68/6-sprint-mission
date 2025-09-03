package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.Map;
import java.util.UUID;

public interface MessageRepository {
    public void save(Message message);
    public Map<UUID, Message> getMessages();
    public boolean delete(Message message);
    public boolean update(Message message);
    public void deleteAll();
}
