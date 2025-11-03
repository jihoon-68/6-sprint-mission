package com.sprint.mission.discodeit.dto.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "공개 채널 생성 요청 DTO")
public record PublicChannelCreateRequestDto (

        @Schema(
                description = "채널 이름 (공백 불가)",
                example = "토론방"
        )
        @NotBlank(message = "채널 이름은 필수 입력값입니다.")
        String name,

        @Schema(
                description = "채널 설명",
                example = "자유롭게 이야기하는 공개 채널입니다.",
                nullable = true
        )
        String description
){
}
