package com.sprint.mission.discodeit.serviece;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserServiece {
  void createUser(User user);
  User readUser(UUID userId);
  void updateUser(User user);
  void deleteUser(UUID userId);
  List<User> readAllInfo();
}
