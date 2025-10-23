package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContentType;

public record BinaryContentCreateRequestDto(

        String fileName,
        String extension, // 파일 확장자
        BinaryContentType type, // PROFILE_IMAGE 또는 ATTACH_IMAGE
        byte[] bytes
) {}
