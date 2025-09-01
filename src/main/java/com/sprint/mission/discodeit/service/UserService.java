package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User user);
    User readUser(UUID userId);
    List<User> readAll();
    User updateUser(UUID userId, User updatedUser);
    boolean deleteUser(UUID userId);

}


