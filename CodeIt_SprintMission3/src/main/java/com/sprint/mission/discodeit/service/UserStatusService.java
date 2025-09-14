package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.CreateUserStatusRequest;
import com.sprint.mission.discodeit.dto.UserStatusResponse;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatusResponse create(CreateUserStatusRequest request);

    UserStatusResponse find(UUID userStatusId);

    List<UserStatusResponse> findAll();

    UserStatusResponse update(UUID userStatusId);

    UserStatusResponse updateByUserId(UUID userId);

    void delete(UUID userStatusId);
}
