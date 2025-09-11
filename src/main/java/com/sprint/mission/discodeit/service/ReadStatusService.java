package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTO.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;

import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(CreateReadStatusDTO createReadStatusDTO);
    ReadStatus findById(UUID id);
    List<ReadStatus> findAllByUserId(UUID userId);
    void update(UpdateReadStatusDTO updateReadStatusDTO);
    void delete(UUID id);
}
