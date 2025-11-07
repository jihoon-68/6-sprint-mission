package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.*;

public record PublicChannelCreateRequest(
    @NotBlank(message = "채널 이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "채널 이름은 1자 이상 50자 이하로 입력해야 합니다.")
    String name,

    @Size(max = 200, message = "채널 설명은 최대 200자까지 가능합니다.")
    String description
) {

}
