package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.time.Instant;
import java.util.List;

import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(CreateReadStatusDTO createReadStatusDTO);
    ReadStatus findById(UUID id);
    List<ReadStatus> findAllByUserId(UUID userId);
    ReadStatus update(UUID readStatusId, Instant newLastReadAt);
    void delete(UUID id);
}
