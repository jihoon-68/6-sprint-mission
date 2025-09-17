package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.UserStatus.CreateUserStatusDTO;
import com.sprint.mission.discodeit.DTOs.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(CreateUserStatusDTO userStatusDto);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    UserStatus update(UpdateUserStatusDTO updateUserStatusDto);
    UserStatus updateByUserId(UUID userId);
    void delete(UUID id);
}
