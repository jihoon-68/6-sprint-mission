package com.sprint.mission.discodeit.dto.userstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Schema(description = "사용자 상태 변경 요청 DTO")
public record UserStatusUpdateRequestDto(

        @NotNull(message = "마지막 접속시간을 입력해 주세요.")
        Instant newLastActiveAt
) {
}
