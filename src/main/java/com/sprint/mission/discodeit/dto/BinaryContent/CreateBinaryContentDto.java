package com.sprint.mission.discodeit.dto.BinaryContent;

import java.time.Instant;
import java.util.UUID;

public record CreateBinaryContentDto(
         UUID profileId,
         UUID messageId,
         String attatchmentUrl
) {
}
