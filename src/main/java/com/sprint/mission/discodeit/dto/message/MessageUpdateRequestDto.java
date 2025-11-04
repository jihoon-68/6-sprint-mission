package com.sprint.mission.discodeit.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "메시지 수정 요청 DTO")
public record MessageUpdateRequestDto(

        @NotBlank(message = "메시지 내용을 입력해 주세요.")
        String newContent
) {}
