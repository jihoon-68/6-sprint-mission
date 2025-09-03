package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User create(String username, String email, String password);
    Optional<User> readId(UUID id);
    Optional<User> readUsername(String username);
    Optional<User> readEmail(String email);
    List<User> readAll();
    boolean update(UUID id, String username, String email, String password);
    boolean delete(UUID id);
}
