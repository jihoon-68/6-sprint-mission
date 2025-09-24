package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.userstatusdto.CreateUserStatus;
import com.sprint.mission.discodeit.dto.userstatusdto.UpdateUserStatus;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {

  UserStatus create(CreateUserStatus createUserStatus);

  UserStatus find(UUID userStatusId);

  List<UserStatus> findAll();

  UserStatus update(UUID userStatusId, UpdateUserStatus updateUserStatus);

  UserStatus updateByUserId(UUID userId);

  void delete(UUID userStatusId);
}
