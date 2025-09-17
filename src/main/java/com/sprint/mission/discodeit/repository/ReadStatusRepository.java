package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.Set;
import java.util.UUID;

public interface ReadStatusRepository extends CrudRepository<ReadStatus, UUID> {

    Set<ReadStatus> findAll(UUID userId);

    void delete(UUID userId, UUID channelId);
}
