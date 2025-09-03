package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(String email, String password, String username);

    void updateUsername(String username, UUID id);

    void updatePassword(String password, UUID id);

    void updateEmail(String email, UUID id);

    void deleteUserById(UUID id);

    User findUserById(UUID id);

    List<User> findAllUsers();

    User login(String email, String password);

    void signup(String email, String password, String username);

    void sendFriendRequest(UUID id, String email);

    void acceptFriendRequest(UUID userid, UUID friendId);

    void rejectFriendRequest(UUID userId, UUID friendId);

    void cancelSendFriendRequest(UUID userId, UUID friendId);

    void deleteFriend(UUID userId, UUID friendId);
}
