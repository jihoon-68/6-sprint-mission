package com.sprint.mission.discodeit.dto.userstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "사용자 상태 생성 요청 DTO")
public record UserStatusCreateRequestDto(

        @NotNull(message = "사용자 ID를 입력해 주세요.")
        UUID userId,

        @NotNull(message = "마지막 접속시간을 입력해 주세요.")
        Instant lastActiveAt
){}
