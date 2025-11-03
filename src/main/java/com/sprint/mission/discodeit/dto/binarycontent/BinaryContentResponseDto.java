package com.sprint.mission.discodeit.dto.binarycontent;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
public record BinaryContentResponseDto(
        UUID id,
        String fileName,
        // String extension,
        Long size
        // BinaryContentType type
        // byte[] data
) {}