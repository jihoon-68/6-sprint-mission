package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.userstatusdto.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(UserStatusDto userStatusDto);
    UserStatus find(UUID userStatusId);
    List<UserStatus> findAll();
    UserStatus update(UserStatusDto userStatusDto);
    UserStatus updateByUserId(UUID userId);
    void delete(UUID userStatusId);
}
