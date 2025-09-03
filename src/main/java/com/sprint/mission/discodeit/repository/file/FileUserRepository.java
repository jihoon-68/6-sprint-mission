package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private final FileStore<User> store;

    public FileUserRepository(String path) { this.store = new FileStore<>(path); }

    @Override
    public User save(User user) {
        Map<UUID, User> map = store.loadMapOrEmpty();
        map.put(user.getId(), user);
        store.saveMap(map);
        return user;
    }

    @Override
    public Optional<User> readId(UUID id) {
        return Optional.ofNullable(store.loadMapOrEmpty().get(id));
    }

    @Override
    public Optional<User> readUsername(String username) {
        return store.loadMapOrEmpty().values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> readEmail(String email) {
        return store.loadMapOrEmpty().values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> readAll() {
        return List.copyOf(store.loadMapOrEmpty().values());
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, User> map = store.loadMapOrEmpty();
        boolean removed = map.remove(id) != null;
        if (removed) { store.saveMap(map); }
        return removed;
    }
}
