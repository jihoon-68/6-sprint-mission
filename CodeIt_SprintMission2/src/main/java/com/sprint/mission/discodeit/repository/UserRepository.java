package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    User createUser(String name, String email);

    User getUserById(UUID id);

    List<User> getAllUsers();

    User updateUser(UUID id, String newName, String newEmail);

    boolean deleteUser(UUID id);
}
