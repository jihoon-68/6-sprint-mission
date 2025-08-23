package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    // Create
    User create(String username, String email, String password);

    // Read (single)
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Read (multiple)
    List<User> findAll();

    // Update
    Optional<User> update(UUID id, String username, String email, String password);

    // Delete
    void delete(UUID id);
}
