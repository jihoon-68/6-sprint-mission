package com.sprint.mission.discodeit.dto.userdto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

public record CreateUserRequest(
    @NotBlank String username,
    @Email String email,
    @NotBlank String password
) {

  public User toEntity(UUID profileId) {
    if (profileId == null) {
      return new User(username, email, password);
    }
    return new User(username, email, password, profileId);
  }

//  public BinaryContent toBinaryContent() {
//    return new BinaryContent(profile);
//  }
}
