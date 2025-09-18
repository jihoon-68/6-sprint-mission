package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.readstatusdto.CreateReadStatusDto;
import com.sprint.mission.discodeit.dto.readstatusdto.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(CreateReadStatusDto createReadStatusDto);
    ReadStatus find(UUID readStatusId);
    List<ReadStatus> findAllByUserId(UUID userId);
    ReadStatus update(UUID readStatusId, UpdateReadStatusDto updateReadStatusDto);
    void delete(UUID readStatusId);
}
