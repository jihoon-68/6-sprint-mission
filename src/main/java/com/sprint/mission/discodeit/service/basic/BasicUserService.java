package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository users;

    public BasicUserService(UserRepository users) {
        this.users = users;
    }

    @Override
    public User create(String username, String email, String password) {
        return users.save(new User(username, email, password));
    }

    @Override
    public Optional<User> readId(UUID id) {
        return users.readId(id);
    }

    @Override
    public Optional<User> readUsername(String username) {
        return users.readUsername(username);
    }

    @Override
    public Optional<User> readEmail(String email) {
        return users.readEmail(email);
    }

    @Override
    public List<User> readAll() {
        return users.readAll();
    }

    @Override
    public boolean update(UUID id, String username, String email, String password) {
        Optional<User> user = users.readId(id);
        if (user.isEmpty()) return false;
        user.get().update(username, email, password);
        users.save(user.get());
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return users.delete(id);
    }
}
