package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.readstatusdto.CreateReadStatus;
import com.sprint.mission.discodeit.dto.readstatusdto.UpdateReadStatus;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatus create(CreateReadStatus createReadStatus);
    ReadStatus find(UUID readStatusId);
    List<ReadStatus> findAllByUserId(UUID userId);
    ReadStatus update(UUID readStatusId, UpdateReadStatus updateReadStatus);
    void delete(UUID readStatusId);
}
