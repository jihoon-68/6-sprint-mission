package com.sprint.mission.discodeit.dto.User;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public record UpdateUserDTO(
        UUID id,
        String username,
        String email,
        BinaryContent profile,
        String password,
        UserStatus status
) {

    public static UpdateUserDTO getStatus(UserStatus status) {
        return new UpdateUserDTO(
                null,
                null,
                null,
                null,
               null,
                status

        );
    }
    public static UpdateUserDTO getUpdateUser(UUID userId, UserUpdateRequest userUpdateRequest, BinaryContent binaryContent ) {
        return new UpdateUserDTO(
                userId,
                userUpdateRequest.newUsername(),
                userUpdateRequest.newEmail(),
                binaryContent,
                userUpdateRequest.newPassword(),
                null
        );
    }

}
