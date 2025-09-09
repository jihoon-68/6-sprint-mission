package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.entity.ReadStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(UUID userId, UUID channelId);
    ReadStatus find(UUID readStatusId);
    List<ReadStatus> findAllByUserId(UUID userId);
    ReadStatus update(UUID userId, Instant ReadAt);
    void delete(UUID readStatusId);
}
