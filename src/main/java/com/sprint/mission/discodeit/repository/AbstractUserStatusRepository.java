package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.USER_STATUS_ID_ALREADY_EXISTS;

public abstract class AbstractUserStatusRepository implements UserStatusRepository {

    protected AbstractUserStatusRepository() {
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        Validator<UserStatus> validator = Validator.identity();
        if (userStatus.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    UserStatus::getId,
                    () -> new IllegalStateException(USER_STATUS_ID_ALREADY_EXISTS.formatted(userStatus.getId()))
            ));
        }
        Map<UUID, UserStatus> data = getData();
        UserStatus validated = validator.validate(data, userStatus);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Optional<UserStatus> find(UUID id) {
        Map<UUID, UserStatus> data = getData();
        UserStatus userStatus = data.get(id);
        return Optional.ofNullable(userStatus);
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        Map<UUID, UserStatus> data = getData();
        Map<UUID, UserStatus> userIdIndex = groupByUserId(data);
        UserStatus userStatus = userIdIndex.get(userId);
        return Optional.ofNullable(userStatus);
    }

    @Override
    public Set<UserStatus> findAll() {
        Map<UUID, UserStatus> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, UserStatus> data = getData();
        data.remove(id);
        flush(data);
    }

    private Map<UUID, UserStatus> groupByUserId(Map<UUID, UserStatus> data) {
        return data.values()
                .stream()
                .collect(Collectors.toMap(UserStatus::getUserId, Function.identity()));
    }

    protected abstract Map<UUID, UserStatus> getData();

    protected void flush(Map<?, ?> data) {
    }
}
