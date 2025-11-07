package com.sprint.mission.discodeit.dto.readstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "읽음 상태 생성 요청 DTO")
public record ReadStatusCreateRequestDto (

        @NotNull(message = "사용자 ID는 필수 입력값입니다.")
        UUID userId,

        @NotNull(message = "채널 ID는 필수 입력값입니다.")
        UUID channelId,

        Instant lastReadAt
){
}
