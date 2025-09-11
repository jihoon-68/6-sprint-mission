package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFUserStatusRepository implements UserStatusRepository {
    private final List<UserStatus> userStatusesDate;

    public JCFUserStatusRepository(List<UserStatus> userStatuses) {
        this.userStatusesDate = userStatuses;
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        int idx = userStatusesDate.indexOf(userStatus);
        if (idx >=0) {
            userStatusesDate.set(idx, userStatus);
        }else {
            userStatusesDate.add(userStatus);
        }
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return userStatusesDate.stream()
                .filter(rs -> rs.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return userStatusesDate.stream()
                .filter(rs -> rs.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        return List.copyOf(userStatusesDate);
    }

    @Override
    public boolean existsById(UUID id) {
        return userStatusesDate.stream()
                .anyMatch(rs -> rs.getId().equals(id));
    }

    @Override
    public void deleteById(UUID id) {
        userStatusesDate.removeIf(rs -> rs.getId().equals(id));
    }
}
