package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final Path file = Paths.get("users.ser");
    private Map<UUID, User> store = new HashMap<>();

    public FileUserRepository() {
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (Files.exists(file)) {
            try (var in = new ObjectInputStream(Files.newInputStream(file))) {
                store = (Map<UUID, User>) in.readObject();
            } catch (Exception ignored) {
                store = new HashMap<>();
            }
        }
    }

    private void persist() {
        try (var out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(store);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public User save(User user) {
        store.put(user.getId(), user);
        persist();
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public User findById(UUID id) {
        return store.get(id);
    }

    @Override
    public boolean deleteById(UUID id) {
        if (existsById(id)) {
            store.remove(id);
            return true;
        } else {
            System.out.println("존재하지 않음");
            return false;
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }
}
