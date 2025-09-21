package com.sprint.mission.discodeit.dto.userdto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUser(
        @NotBlank String newUsername,
        @NotBlank String newEmail,
        @NotBlank String newPassword,
        boolean online
) {
}
