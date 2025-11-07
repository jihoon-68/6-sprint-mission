package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.*;

public record LoginRequest(
    @NotBlank(message = "username은 필수입니다.")
    @Size(min = 3, max = 30, message = "username은 3~30자 사이여야 합니다.")
    String username,

    @NotBlank(message = "password는 필수입니다.")
    @Size(min = 6, max = 100, message = "password는 6~100자 사이여야 합니다.")
    String password
) {

}
