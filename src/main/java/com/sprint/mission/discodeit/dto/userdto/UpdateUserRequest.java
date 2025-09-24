package com.sprint.mission.discodeit.dto.userdto;

import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
    String newUsername,
    String newEmail,
    String newPassword
) {

}
