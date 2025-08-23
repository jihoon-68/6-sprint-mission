package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JCFUserService implements UserService{
    private final Map<UUID, User> data;
    private static JCFUserService instance;

    private JCFUserService() {
        this.data = new ConcurrentHashMap<>();
    }

    public static JCFUserService getInstance() {
        if (instance == null) {
            instance = new JCFUserService();
        }
        return instance;
    }

    @Override
    public User create(String username, String email, String password) {
        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(username, email, password);
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return data.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return data.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<User> update(UUID id, String username, String email, String password) {
        User user = data.get(id);
        if (user == null) {
            return Optional.empty();
        }
        if (username != null && !username.equals(user.getUsername())) {
            Optional<User> existUser = findByUsername(username);
            if (existUser.isPresent() && !existUser.get().getId().equals(id)) {
                throw new IllegalArgumentException("Username already exists");
            }
        }
        if (email != null && !email.equals(user.getEmail())) {
            Optional<User> existUser = findByEmail(email);
            if (existUser.isPresent() && !existUser.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        user.update(username, email, password);
        return Optional.of(user);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
