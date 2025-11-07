package com.sprint.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "공개 채널 생성 요청 DTO")
public record PublicChannelCreateRequestDto (

        @Size(max = 20, message = "채널명은 20자까지 입력 가능합니다.")
        @Schema(description = "채널 이름 (공백 불가)", example = "토론방")
        @NotBlank(message = "채널 이름을 입력해 주세요.")
        String name,

        @Schema(description = "채널 설명", example = "토론방입니다.", nullable = true)
        @Size(max = 100, message = "채널 설명은 100자까지 입력 가능합니다.")
        String description
){
}
