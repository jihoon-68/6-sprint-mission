package com.sprint.mission.discodeit.dto.userdto;

public record UpdateUser(
        String newUsername,
        String newEmail,
        String newPassword,
        boolean online
) {
}
