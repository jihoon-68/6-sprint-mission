package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {

    private final Map<UUID, User> data;

    public JCFUserRepository() {
        data = new HashMap<>();
    }

    @Override
    public User save(User user) {
        data.put(user.id(), user);
        return user;
    }

    @Override
    public Optional<User> find(UUID id) {
        User user = data.get(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
