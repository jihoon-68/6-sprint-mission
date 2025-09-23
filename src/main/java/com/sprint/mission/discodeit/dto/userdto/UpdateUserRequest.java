package com.sprint.mission.discodeit.dto.userdto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
    @NotBlank String newUsername,
    @NotBlank String newEmail,
    @NotBlank String newPassword,
    byte[] profile,
    boolean online
) {

}
