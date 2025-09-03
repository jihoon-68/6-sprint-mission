package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class FileUserService implements UserService {
    private final File file = new File("data/user.ser");

    private Map<UUID, User> loadAll() {
        if (!file.exists()) { return new HashMap<>(); }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<UUID, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    private void saveAll(Map<UUID, User> data) {
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User create(String username, String email, String password) {
        Map<UUID, User> data = loadAll();
        User newUser = new User(username, email, password);
        data.put(newUser.getId(), newUser);
        saveAll(data);
        return newUser;
    }

    @Override
    public Optional<User> readId(UUID id) {
        return Optional.ofNullable(loadAll().get(id));
    }

    @Override
    public Optional<User> readUsername(String username) {
        return loadAll().values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> readEmail(String email) {
        return loadAll().values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(loadAll().values());
    }

    @Override
    public boolean update(UUID id, String username, String email, String password) {
        Map<UUID, User> data = loadAll();
        User userToUpdate = data.get(id);
        if (userToUpdate == null) {
            return false;
        }
        userToUpdate.update(username, email, password);
        data.put(id, userToUpdate);
        saveAll(data);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        Map<UUID, User> data = loadAll();
        boolean removed = data.remove(id) != null;
        if (removed) { saveAll(data); }
        return removed;
    }
}
