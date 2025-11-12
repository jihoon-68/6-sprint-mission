package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.*;

public record UserUpdateRequest(
    @Size(min = 2, max = 30, message = "새 사용자 이름은 2자 이상 30자 이하로 입력해야 합니다.")
    String newUsername,

    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    String newEmail,

    @Size(min = 6, max = 50, message = "새 비밀번호는 6자 이상 50자 이하로 입력해야 합니다.")
    String newPassword
) {

}
