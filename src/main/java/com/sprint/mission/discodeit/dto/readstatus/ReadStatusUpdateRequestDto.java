package com.sprint.mission.discodeit.dto.readstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Schema(description = "읽음 상태 수정 요청 DTO")
public record ReadStatusUpdateRequestDto (

        @NotNull(message = "마지막으로 읽은 시간을 입력해 주세요.")
        Instant newLastReadAt
){}
