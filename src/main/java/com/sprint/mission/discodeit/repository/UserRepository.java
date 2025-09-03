package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findByUserName(String name);
    User findByEmail(String email);
    List<User> findAll();
    void delete(User user);
}
