package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public void createUser(String email, String password, String username);
    public void updateUsername(String username, UUID id);
    public void updatePassword(String password, UUID id);
    public void updateEmail(String email, UUID id);
    public void deleteUserById(UUID id);
    public User findUserById(UUID id);
    public List<User> findAllUsers();
    public User login(String email, String password);
    public void signup(String email, String password, String username);
}
