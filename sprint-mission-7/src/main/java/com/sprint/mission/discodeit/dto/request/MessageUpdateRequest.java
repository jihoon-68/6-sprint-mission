package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.*;

public record MessageUpdateRequest(

    @NotBlank(message = "메시지 내용은 비어 있을 수 없습니다.")
    @Size(min = 1, max = 1000, message = "메시지 내용은 1자 이상 1000자 이하로 입력해야 합니다.")
    String newContent
) {

}
