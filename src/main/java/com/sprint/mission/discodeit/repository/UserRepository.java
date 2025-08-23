package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    void save(User user);

    void deleteById(UUID id);

    User findById(UUID id);

    User findByEmail(String email);

    List<User> findAll();

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    boolean existsByPassword(String password);
}
