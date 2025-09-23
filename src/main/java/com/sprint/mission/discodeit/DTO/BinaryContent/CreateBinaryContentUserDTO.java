package com.sprint.mission.discodeit.DTO.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentUserDTO(
        UUID userId,
        String filePath
) {
}
