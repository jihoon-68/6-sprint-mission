package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.dto.userStatus.response.UserStatusFindAllResponse;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusRepository {
    UserStatus findByUserId(UUID userId);
    void save(UserStatus userStatus);

    void deleteByUserId(UUID id);

    List<UserStatus> findAll();
}
