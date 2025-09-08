package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTOs.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(CreateReadStatusDTO createReadStatusDto);
    ReadStatus find(UUID id);
    List<ReadStatus> findAllByUserId(UUID userId);
    ReadStatus update(UpdateReadStatusDTO updateReadStatusDto);
    void delete(UUID id);
}