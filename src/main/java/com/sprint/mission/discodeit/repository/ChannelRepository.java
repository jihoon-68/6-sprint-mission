package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ChannelRepository extends CrudRepository<Channel, UUID> {

    Optional<Channel> findPublic(UUID id);

    Optional<Channel> findPrivate(UUID id);

    Set<Channel> findAllPublic();

    Set<Channel> findAllPrivate();
}
