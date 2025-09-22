package com.sprint.mission.discodeit.message.repository;

import com.sprint.mission.discodeit.common.persistence.CrudRepository;
import com.sprint.mission.discodeit.message.domain.Message;

import java.time.Instant;
import java.util.UUID;

public interface MessageRepository extends CrudRepository<Message, UUID> {

    Instant maxCreatedAtByChannelId(UUID channelId);

    Iterable<Message> findAllByChannelId(UUID channelId);
}
