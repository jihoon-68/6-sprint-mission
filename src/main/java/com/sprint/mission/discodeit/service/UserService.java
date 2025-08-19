package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;

public interface UserService {
    public boolean createUser(String userName, String email);
    public boolean deleteUser(User user);
    public boolean updateUser(User user);
    public List<User> getUsers();
    public User getUser(String email);
}
