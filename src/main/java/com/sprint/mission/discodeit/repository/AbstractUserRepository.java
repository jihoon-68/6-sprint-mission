package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.repository.RepositoryMessageConstants.*;

public abstract class AbstractUserRepository implements UserRepository {

    protected AbstractUserRepository() {
    }

    @Override
    public User save(User user) {
        Validator<User> validator = Validator.identity();
        if (user.isNew()) {
            validator = validator.and(Validator.uniqueKey(
                    User::getId,
                    () -> new IllegalStateException(USER_ID_ALREADY_EXISTS.formatted(user.getId()))
            ));
        }
        validator = validator.and(Validator.uniqueKey(
                User::getNickname,
                () -> new IllegalStateException(USER_NICKNAME_ALREADY_EXISTS.formatted(user.getNickname()))
        )).and(Validator.uniqueKey(
                User::getMail,
                () -> new IllegalStateException(USER_MAIL_ALREADY_EXISTS.formatted(user.getMail()))
        ));
        Map<UUID, User> data = getData();
        User validated = validator.validate(data, user);
        data.put(validated.getId(), validated);
        flush(data);
        return validated;
    }

    @Override
    public Optional<User> find(UUID id) {
        Map<UUID, User> data = getData();
        User user = data.get(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> find(String nickname) {
        Map<UUID, User> data = getData();
        Map<String, User> nicknameToUser = groupByNickname(data);
        User user = nicknameToUser.get(nickname);
        return Optional.ofNullable(user);
    }

    @Override
    public Set<User> findAll() {
        Map<UUID, User> data = getData();
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, User> data = getData();
        data.remove(id);
        flush(data);
    }

    private Map<String, User> groupByNickname(Map<UUID, User> data) {
        return data.values()
                .stream()
                .collect(Collectors.toMap(User::getNickname, Function.identity()));
    }

    protected abstract Map<UUID, User> getData();

    protected void flush(Map<?, ?> data) {
    }
}
