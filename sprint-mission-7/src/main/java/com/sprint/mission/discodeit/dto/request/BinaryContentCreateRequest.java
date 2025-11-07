package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.*;

public record BinaryContentCreateRequest(
    @NotBlank(message = "파일명은 필수입니다.")
    @Size(max = 255, message = "파일명은 최대 255자입니다.")
    String fileName,

    @NotBlank(message = "콘텐츠 타입은 필수입니다.")
    @Size(max = 100, message = "콘텐츠 타입은 최대 100자입니다.")
    String contentType,

    @NotNull(message = "파일 데이터가 필요합니다.")
    @Size(min = 1, max = 10_485_760, message = "파일 크기는 1바이트 이상 10MB 이하만 허용됩니다.")
    byte[] bytes
) {

}
