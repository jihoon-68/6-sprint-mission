package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.util.*;

public class FileUserService implements UserService {
    private final SerializableStore<User> store;
    private final Map<UUID, User> data;

    public FileUserService(String baseDir) {
        this.store = new SerializableStore<>(new File(baseDir, "users.ser"));
        this.data = store.load();
    }

    @Override
    public User create(String username, String email, String password) {
        User u = new User(username, email, password);
        data.put(u.getId(), u);
        store.save(data);
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
        store.save(data);
        return Optional.of(u);
    }

    @Override
    public boolean delete(UUID id) {
        boolean removed = data.remove(id) != null;
        if (removed) store.save(data);
        return removed;
    }
}
