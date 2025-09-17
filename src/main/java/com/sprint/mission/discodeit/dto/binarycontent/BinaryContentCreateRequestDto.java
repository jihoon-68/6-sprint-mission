package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContentType;

import java.util.UUID;

public record BinaryContentCreateRequestDto(
        UUID userId,
        UUID messageId, // 프사의 경우 없을 수 있음
        BinaryContentType type,
        byte[] byteBuffer
) {}
