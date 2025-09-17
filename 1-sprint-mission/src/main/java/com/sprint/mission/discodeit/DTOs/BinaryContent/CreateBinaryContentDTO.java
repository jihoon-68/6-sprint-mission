package com.sprint.mission.discodeit.DTOs.BinaryContent;

import java.time.Instant;
import java.util.UUID;

public record CreateBinaryContentDTO(
        UUID profileId,
        UUID messageId,
        String attatchmentUrl
) {
}