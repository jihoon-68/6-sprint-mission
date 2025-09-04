package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String username, String email, String password) {
        return userRepository.save(new User(username, email, password));
    }

    @Override
    public Optional<User> readId(UUID id) {
        return userRepository.readId(id);
    }

    @Override
    public Optional<User> readUsername(String username) {
        return userRepository.readUsername(username);
    }

    @Override
    public Optional<User> readEmail(String email) {
        return userRepository.readEmail(email);
    }

    @Override
    public List<User> readAll() {
        return userRepository.readAll();
    }

    @Override
    public boolean update(UUID id, String username, String email, String password) {
        Optional<User> user = userRepository.readId(id);
        if (user.isEmpty()) { return false; }
        user.get().update(username, email, password);
        userRepository.save(user.get());
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return userRepository.delete(id);
    }
}
