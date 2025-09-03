package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService() {
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public User create(String username, String email, String password) {
        User u = new User(username, email, password);
        data.put(u.getId(), u);
        return u;
    }

    @Override
    public Optional<User> findById(UUID id) { return Optional.ofNullable(data.get(id)); }

    @Override
    public List<User> findAll() { return new ArrayList<>(data.values()); }

    @Override
    public Optional<User> update(UUID id, String username, String email, String password) {
        User u = data.get(id);
        if (u == null) return Optional.empty();
        u.update(username, email, password);
        return Optional.of(u);
    }

    @Override
    public boolean delete(UUID id) { return data.remove(id) != null; }
}
