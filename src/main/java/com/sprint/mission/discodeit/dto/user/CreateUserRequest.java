package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotEmpty(message = "{user.name.notempty}")
    @Size(max = 50)
    String username,
    @NotEmpty(message = "{user.email.notempty}")
    @Email(message = "{user.email.invalid}")
    @Size(max = 100)
    String email,
    @Size(min = 4, max = 60, message = "{user.password.size}")
    String password
) {

}
