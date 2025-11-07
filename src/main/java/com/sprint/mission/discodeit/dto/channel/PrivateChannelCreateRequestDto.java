package com.sprint.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Schema(description = "비공개 채널 생성 요청 DTO")
public record PrivateChannelCreateRequestDto (

        @NotNull(message = "채널 구성원 목록은 필수 입력값입니다.")
        List<UUID> participantIds
){}
