package com.sprint.mission.discodeit.dto.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContentType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record BinaryContentResponseDto(
        UUID id,
        String fileName,
        String extension,
        Long size,
        BinaryContentType type
        // byte[] data
) {}