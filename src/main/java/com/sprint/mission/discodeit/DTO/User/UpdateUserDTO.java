package com.sprint.mission.discodeit.DTO.User;

import java.util.UUID;

public record UpdateUserDTO(
        UUID id,
        String userName,
        String email,
        UUID profileId,
        String password
) {
    static public UpdateUserDTO getFileInput(UUID profileId) {
        return new UpdateUserDTO(
                null,
                null,
                null,
                profileId,
               null);
    }
}
