package com.sprint.mission.discodeit.dto.User;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public record UpdateUserDto(
        UUID id,
        String username,
        String email,
        BinaryContent profile,
        String password,
        UserStatus status
) {

    public static UpdateUserDto getStatus(UserStatus status) {
        return new UpdateUserDto(
                null,
                null,
                null,
                null,
               null,
                status

        );
    }
    public static UpdateUserDto getUpdateUser(UUID userId, UserUpdateRequest userUpdateRequest, BinaryContent binaryContent ) {
        return new UpdateUserDto(
                userId,
                userUpdateRequest.newUsername(),
                userUpdateRequest.newEmail(),
                binaryContent,
                userUpdateRequest.newPassword(),
                null
        );
    }

}
