package com.sprint.mission.discodeit.dto.userdto;

import java.util.UUID;

public record UpdateUserDto (
        UUID userId,
        String newUsername,
        String newEmail,
        String newPassword,
        String newImagePath
) {
    public UpdateUserDto(UUID userId, String newUsername, String newEmail, String newPassword) {
        this(userId, newUsername, newEmail, newPassword, null);
    }
}
