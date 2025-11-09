package com.sprint.mission.discodeit.dto.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentUserDTO(
        UUID userId,
        String filePath
) {
}
