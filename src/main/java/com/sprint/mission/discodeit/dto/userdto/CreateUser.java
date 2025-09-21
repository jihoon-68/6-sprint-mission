package com.sprint.mission.discodeit.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUser(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        byte[] bytes
) {
}
