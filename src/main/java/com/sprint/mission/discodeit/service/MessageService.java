package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.*;
import java.util.UUID;

public interface MessageService {
    Message create(String content, UUID channelId, UUID authorId);
    Optional<Message> findById(UUID id);
    List<Message> findAll();
    Optional<Message> update(UUID id, String content, UUID channelId, UUID authorId);
    boolean delete(UUID id);
}
