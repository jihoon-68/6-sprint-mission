package com.sprint.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "공개 채널 정보 수정 요청 DTO")
public record PublicChannelUpdateRequestDto (

        @Size(max = 20, message = "채널명은 20자까지 입력 가능합니다.")
        @Schema(nullable = true, description = "새 채널명. 형식 검증은 서비스 레이어에서 수행")
        String newName,

        @Size(max = 100, message = "채널 설명은 100자까지 입력 가능합니다.")
        @Schema(nullable = true)
        String newDescription
) {
}
