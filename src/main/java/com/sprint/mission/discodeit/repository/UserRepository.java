package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> readId(UUID id);
    Optional<User> readUsername(String username);
    Optional<User> readEmail(String email);
    List<User> readAll();
    boolean delete(UUID id);
}
