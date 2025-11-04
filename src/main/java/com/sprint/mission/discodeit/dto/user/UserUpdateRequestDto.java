package com.sprint.mission.discodeit.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "사용자 정보 변경 요청 DTO")
public record UserUpdateRequestDto (

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Size(max = 50, message = "이메일은 50자까지 입력 가능합니다.")
        @Schema(nullable = true)
        String newEmail,

        @Size(max = 20, message = "닉네임은 20자까지 입력 가능합니다.")
        @Schema(nullable = true, description = "새 닉네임. 형식 검증은 서비스 레이어에서 수행")
        String newUsername,

        @Size(max = 50, message = "비밀번호는 50자까지 입력 가능합니다.")
        @Schema(nullable = true, description = "새 비밀번호. 형식 검증은 서비스 레이어에서 수행")
        String newPassword
){}
