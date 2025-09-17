package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, UUID> {

    Optional<Instant> findLastCreatedAt(UUID channelId);

    Set<Message> findAll(UUID channelId);
}
