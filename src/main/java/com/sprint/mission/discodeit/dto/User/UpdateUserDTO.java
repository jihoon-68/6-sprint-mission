package com.sprint.mission.discodeit.dto.User;

import java.util.UUID;

public record UpdateUserDTO(
        UUID id,
        String username,
        String email,
        UUID profileId,
        String password
) {

    public static UpdateUserDTO getFileInput(UUID profileId) {
        return new UpdateUserDTO(
                null,
                null,
                null,
                profileId,
               null);
    }
    public static UpdateUserDTO getUpdateUser(UUID userId, userUpdateRequest userUpdateRequest) {
        return new UpdateUserDTO(
                userId,
                userUpdateRequest.newUsername(),
                userUpdateRequest.newEmail(),
                null,
                userUpdateRequest.newPassword()
        );
    }

}
