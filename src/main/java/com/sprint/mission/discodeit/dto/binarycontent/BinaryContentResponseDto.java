package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContentType;

import java.time.Instant;
import java.util.UUID;

public record BinaryContentResponseDto(
        UUID id,
        UUID userId,
        UUID messageId,
        BinaryContentType type,
        Instant createdAt
) {}