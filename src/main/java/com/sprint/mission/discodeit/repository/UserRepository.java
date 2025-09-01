package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User create(User user);
    User read(UUID id);
    List<User> readAll();
    User update(UUID id, User user);
    boolean delete(UUID id);
}
