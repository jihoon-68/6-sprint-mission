package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateUserRequest(
    @NotBlank(message = "{user.name.notblank}")
    String username,
    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.invalid}")
    String email,
    @Size(min = 4, message = "{user.password.size}")
    String password
) {

  public User toEntity(UUID profileId) {
    if (profileId == null) {
      return new User(username, email, password);
    }
    return new User(username, email, password, profileId);
  }
}
