package com.sprint.mission.discodeit.dto.userdto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUser(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        byte[] bytes
) {
    public User toEntity(BinaryContent binaryContent) {
        return new User(username,email,password, binaryContent.getId());
    }
    public BinaryContent toBinaryContent() {
        return new BinaryContent(bytes);
    }
}
