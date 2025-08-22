package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.Map;

public interface UserRepository {
    public boolean save(User user);
    public boolean delete(User user);
    public boolean update(User user);
    public Map<String, User> getAllUsers();
}
