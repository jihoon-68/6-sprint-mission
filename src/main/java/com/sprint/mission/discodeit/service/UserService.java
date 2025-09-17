package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.model.UserDto;
import com.sprint.mission.discodeit.dto.user.request.UserUpdateProfileImageRequest;
import com.sprint.mission.discodeit.dto.user.response.UserFindAllResponse;

import java.util.UUID;

public interface UserService {
    void updateUsername(String username, UUID id);

    void updatePassword(String password, UUID id);

    void updateEmail(String email, UUID id);

    void updateProfileImage(UserUpdateProfileImageRequest request);

    void deleteUserById(UUID id);

    UserDto findUserById(UUID id);

    UserFindAllResponse findAllUsers();

    void sendFriendRequest(UUID id, String email);

    void acceptFriendRequest(UUID userid, UUID friendId);

    void rejectFriendRequest(UUID userId, UUID friendId);

    void cancelSendFriendRequest(UUID userId, UUID friendId);

    void deleteFriend(UUID userId, UUID friendId);
}
