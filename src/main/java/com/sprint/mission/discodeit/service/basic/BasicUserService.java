package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class BasicUserService implements UserService {
    private final UserRepository repo;

    public BasicUserService(UserRepository repo) { this.repo = repo; }

    @Override
    public User create(String username, String email, String password) {
        // Example simple business rule: email must be unique
        boolean exists = repo.findAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (exists) throw new IllegalArgumentException("Email already in use");
        return repo.save(new User(username, email, password));
    }

    @Override public Optional<User> findById(UUID id) { return repo.findById(id); }
    @Override public List<User> findAll() { return repo.findAll(); }

    @Override
    public Optional<User> update(UUID id, String username, String email, String password) {
        Optional<User> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        User u = opt.get();
        // check email uniqueness if changed
        if (!u.getEmail().equalsIgnoreCase(email)) {
            boolean exists = repo.findAll().stream().anyMatch(x -> !x.getId().equals(id) && x.getEmail().equalsIgnoreCase(email));
            if (exists) throw new IllegalArgumentException("Email already in use");
        }
        u.update(username, email, password);
        repo.save(u);
        return Optional.of(u);
    }

    @Override public boolean delete(UUID id) { return repo.deleteById(id); }
}
