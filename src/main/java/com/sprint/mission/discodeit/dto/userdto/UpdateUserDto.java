package com.sprint.mission.discodeit.dto.userdto;

import java.util.UUID;

public record UpdateUserDto (
        String newUsername,
        String newEmail,
        String newPassword
) {
}
