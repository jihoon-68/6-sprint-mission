package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.util.*;

public class JCFUserStatusRepository implements UserStatusRepository {

    private final Map<UUID, UserStatus> userStatusMap;

    public JCFUserStatusRepository() {
        this.userStatusMap = new HashMap<>();
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        this.userStatusMap.put(userStatus.getId(), userStatus);
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return Optional.ofNullable(this.userStatusMap.get(id));
    }

    @Override
    public List<UserStatus> findAll() {
        return this.userStatusMap.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.userStatusMap.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.userStatusMap.remove(id);
    }
}
