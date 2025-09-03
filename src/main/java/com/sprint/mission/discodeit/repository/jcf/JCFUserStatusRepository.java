package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.util.List;
import java.util.UUID;

public class JCFUserStatusRepository implements UserStatusRepository {
    @Override
    public UserStatus save(UserStatus userStatus) {
        return null;
    }

    @Override
    public UserStatus find(UUID userId) {
        return null;
    }

    @Override
    public List<UserStatus> findAll() {
        return List.of();
    }
}
