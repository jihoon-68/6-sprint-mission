package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFUserStatusRepository implements UserStatusRepository {
    private final Map<UUID, UserStatus> map = new HashMap<>();

    @Override
    public UserStatus save(UserStatus userStatus) {
        map.put(userStatus.getId(), userStatus);
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {

        return map.values()
                .stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<UserStatus> find(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Map<UUID, UserStatus> findAll() {

        return map;
    }

    @Override
    public void deleteByUserId(UUID id) {
        map.entrySet().removeIf(entry -> entry.getValue().getUserId().equals(id));
    }

    @Override
    public void delete(UUID id) {
        map.remove(id);
    }
}
