package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(
        prefix = "discodeit.repository",
        name = "type",
        havingValue = "jcf",
        matchIfMissing = true
)
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
    public Optional<UserStatus> findByUserId(UUID userId) {
        return this.findAll().stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst();
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

    @Override
    public void deleteByUserId(UUID userId) {
        this.findByUserId(userId)
                .ifPresent(userStatus -> this.deleteByUserId(userStatus.getId()));
    }
}
