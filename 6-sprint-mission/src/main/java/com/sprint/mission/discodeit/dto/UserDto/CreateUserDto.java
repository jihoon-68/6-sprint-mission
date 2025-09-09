package com.sprint.mission.discodeit.dto.UserDto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        String imagePath,
        BinaryContent binaryContent
) {
    // imagePath 없이 생성 가능
    // record는 모든 필드를 받는 생성자가 자동으로 만들어짐
    // 다른 생성자 추가해도 모든 필드 받는 생성자는 그대로 존재
    public CreateUserDto(String username, String email, String password) {
        this(username, email, password, null, null);
    }
}
