package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.Map;
import java.util.UUID;

public interface UserRepository {
    public boolean save(User user);
    public boolean delete(User user);
    public boolean update(User user);
    public Map<UUID, User> getAllUsers();
    public void deleteAll();
}
