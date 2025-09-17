package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class JCFUserStatusRepository implements UserStatusRepository {

    private final List<UserStatus> data = new ArrayList<>();

    @Override
    public void save(UserStatus userStatus) {
        data.removeIf(u -> u.getId().equals(userStatus.getId()));
        data.add(userStatus);
    }

    @Override
    public UserStatus findById(UUID id) {
        return data.stream()
                .filter(userStatus -> userStatus.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserStatus findByUserId(UUID userId) {
        return data.stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<UserStatus> findAll() {
        return List.copyOf(data);
    }

    @Override
    public void delete(UserStatus userStatus) {
        boolean removed = data.removeIf(us -> us.getId().equals(userStatus.getId()));
        if (!removed) {
            throw new NoSuchElementException("존재하지 않는 UserStatus입니다. id=" + userStatus.getId());
        }
    }

    @Override
    public void clear() {
        data.clear();
    }
}
