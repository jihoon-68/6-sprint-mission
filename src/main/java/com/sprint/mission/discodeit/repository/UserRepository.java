package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    void enroll(User user);
    User findById(UUID userId);
    void delete(UUID userId);

    List<User> findAll();
}
