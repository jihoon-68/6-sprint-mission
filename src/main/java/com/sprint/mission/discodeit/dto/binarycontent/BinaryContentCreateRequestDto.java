package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.enums.BinaryContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "파일 업로드 요청 DTO")
public record BinaryContentCreateRequestDto(

        @Size(max = 100, message = "파일명은 100자까지 입력 가능합니다.")
        @NotBlank(message = "파일명을 입력해 주세요.")
        String fileName,

        @Size(max = 10, message = "확장자는 10자까지 입력 가능합니다.")
        @NotBlank(message = "파일 확장자를 입력해 주세요.")
        String extension, // 파일 확장자

        @NotNull(message = "파일 타입은 필수 입력값입니다.")
        BinaryContentType type, // PROFILE_IMAGE 또는 ATTACH_IMAGE

        @NotNull(message = "파일 바이트는 필수 입력값입니다.")
        byte[] bytes
) {}