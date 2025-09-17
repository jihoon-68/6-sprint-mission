package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.CreateReadStatusRequest;
import com.sprint.mission.discodeit.dto.ReadStatusResponse;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatusResponse create(CreateReadStatusRequest request);

    ReadStatusResponse find(UUID readStatusId);

    List<ReadStatusResponse> findAllByUserId(UUID userId);

    ReadStatusResponse update(UUID readStatusId);

    void delete(UUID readStatusId);
}
