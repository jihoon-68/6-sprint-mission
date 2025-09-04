package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatus.CreateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UpdateUserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(CreateUserStatusDto userStatusDto);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    UserStatus update(UpdateUserStatusDto  updateUserStatusDto);
    UserStatus updateByUserId(UUID userId);
    void delete(UUID id);
}
