package com.sprint.mission.discodeit.channel.repository;

import com.sprint.mission.discodeit.channel.domain.Channel;
import com.sprint.mission.discodeit.common.persistence.CrudRepository;

import java.util.UUID;

public interface ChannelRepository extends CrudRepository<Channel, UUID> {

    Channel findPublicById(UUID id);

    Channel findPrivateById(UUID id);

    Iterable<Channel> findAllPublic();

    Iterable<Channel> findAllPrivate();
}
