package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

// 엔터티와 구성요소 동일하지만 필요 시 캡슐화 위해 작성.
@Builder
public record BinaryContentResponseDto(
        UUID id,
        String fileName,
        BinaryContentType type,
        byte[] data,
        Instant createdAt
) {}