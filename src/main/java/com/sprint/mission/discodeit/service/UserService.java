package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
      User create(String username, int age , String email);
      User find(UUID id);
      List<User> findAll();
      void update(User user);
      void delete(UUID id);
}
