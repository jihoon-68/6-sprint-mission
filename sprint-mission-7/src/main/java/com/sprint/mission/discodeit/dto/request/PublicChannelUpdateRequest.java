package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.Size;

public record PublicChannelUpdateRequest(
    @Size(min = 1, max = 50, message = "새 채널 이름은 1자 이상 50자 이하로 입력해야 합니다.")
    String newName,

    @Size(max = 200, message = "새 설명은 최대 200자까지 가능합니다.")
    String newDescription
) {

}
