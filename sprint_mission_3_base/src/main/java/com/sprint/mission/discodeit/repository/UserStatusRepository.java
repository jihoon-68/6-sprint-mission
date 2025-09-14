package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository {
    UserStatus save(UserStatus userStatus);
    Optional<UserStatus> findByUserId(UUID userid);
    Optional<UserStatus> find(UUID id);
    Map<UUID, UserStatus> findAll();
    void deleteById(UUID id);
    void delete(UUID id);
}
