package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

public interface UserService {
    User createUser(String email, String userName, String password);
    void updateEmail(User user, String email);
    void updateUserName(User user, String newUserName);
    void updatePassword(User user, String oldPassword, String newPassword);
    void deleteUser(User user, String password);
}
