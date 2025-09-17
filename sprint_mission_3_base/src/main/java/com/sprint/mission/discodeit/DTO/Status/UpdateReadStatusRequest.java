package com.sprint.mission.discodeit.DTO.Status;

import java.time.Instant;
import java.util.UUID;

public record UpdateReadStatusRequest(
        UUID id,
        UUID userId,
        UUID channelId
) {
}
