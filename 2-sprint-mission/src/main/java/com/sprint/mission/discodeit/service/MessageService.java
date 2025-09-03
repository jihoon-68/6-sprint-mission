package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface MessageService {

    Message create(String content, UUID channelId, UUID authorId);

    Optional<Message> read(UUID id);

    Set<Message> readAll();

    Message update(UUID id, String newContent);

    void delete(UUID id);
}
