package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
      void createUser(String username, int age , String email);
      User findUserById(UUID id);
      User findUserByEmail(String userEmail);
      List<User> findAllUsers();
      void updateUser(User user);
      void deleteUser(UUID id);
}
