package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface MessageRepository {

    Message save(Message message);

    Optional<Message> find(UUID id);

    Set<Message> findAll();

    void delete(UUID id);
}
