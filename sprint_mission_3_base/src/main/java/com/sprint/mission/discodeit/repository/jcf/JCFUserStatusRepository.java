package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "repository.type", havingValue = "jcf")
public class JCFUserStatusRepository implements UserStatusRepository {
    private final Map<UUID, UserStatus> data;

    public JCFUserStatusRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        this.data.put(userStatus.getId(), userStatus);
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userid) {
        return data.values().stream().filter(status -> status.getUserId().equals(userid)).findFirst();
    }

    @Override
    public Optional<UserStatus> find(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public Map<UUID, UserStatus> findAll() {
        return this.data;
    }

    @Override
    public void deleteById(UUID id) {
        this.data.entrySet().removeIf(entry -> entry.getValue().getId().equals(id));
    }

    @Override
    public void delete(UUID id) {
        this.data.remove(id);
    }
}
