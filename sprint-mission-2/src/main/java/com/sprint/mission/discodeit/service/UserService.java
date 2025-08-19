package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public void createUser(String username, int age , String email);
    public User findUserById(UUID id);
    public List<User> findAllUsers();
    public User updateUser(User user);
    public void deleteUser(UUID id);
}
