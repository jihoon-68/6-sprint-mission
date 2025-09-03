package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {
    private final List<User> users;

    public JCFUserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public void save(User user) {
        if (existsById(user.getId())) {
            User updateUser = findById(user.getId());
            updateUser.updateUsername(user.getUsername());
            updateUser.updateEmail(user.getEmail());
            updateUser.updatePassword(user.getPassword());
        } else {
            users.add(user);
        }
    }

    @Override
    public void deleteById(UUID id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public User findById(UUID id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public boolean existsById(UUID id) {
        return users.stream().anyMatch(u -> u.getId().equals(id));
    }

    @Override
    public boolean existsByPassword(String password) {
        return users.stream().anyMatch(user -> user.getPassword().equals(password));
    }
}
