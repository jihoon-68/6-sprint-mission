package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User createUser(String username, int age , String email);
    public User findUserById(UUID id);
    public User findUserByUserEmail(String userEmail);
    public List<User> findAllUsers();
    public void updateUser(User user);
    public void deleteUser(UUID id);
}
