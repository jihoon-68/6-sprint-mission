package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    void addUser(User user);
    User readUser(UUID userId);
    void deleteUser(UUID userId);
    void updateUser(User user);
    List<User> readAllUser();
}
