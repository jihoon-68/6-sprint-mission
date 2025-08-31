package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.*;
import java.util.UUID;

public interface UserService {
    User create(String username, String email, String password);
    Optional<User> findById(UUID id);
    List<User> findAll();
    Optional<User> update(UUID id, String username, String email, String password);
    boolean delete(UUID id);
}