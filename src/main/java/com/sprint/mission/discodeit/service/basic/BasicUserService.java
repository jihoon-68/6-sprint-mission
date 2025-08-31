package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository repository;

    public BasicUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(User user) {
        return repository.create(user);
    }

    @Override
    public User readUser(UUID id) {
        return repository.read(id);
    }

    @Override
    public List<User> readAll() {
        return repository.readAll();
    }

    @Override
    public User updateUser(UUID id, User updatedUser) {
        return repository.update(id, updatedUser);
    }

    @Override
    public boolean deleteUser(UUID id) {
        return repository.delete(id);
    }
}
