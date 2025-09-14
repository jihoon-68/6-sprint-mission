package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Status.CreateUserStatusRequest;
import com.sprint.mission.discodeit.DTO.Status.UpdateUserStatusRequest;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;
import java.util.List;

public interface UserStatusService {
    UserStatus create(CreateUserStatusRequest request);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    UserStatus update(UpdateUserStatusRequest request);
    UserStatus updateByUserId(UUID userId);
    void delete(UUID id);
}
