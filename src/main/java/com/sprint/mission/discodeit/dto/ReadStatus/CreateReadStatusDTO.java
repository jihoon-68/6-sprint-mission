package com.sprint.mission.discodeit.dto.ReadStatus;

import java.time.Instant;
import java.util.UUID;

public record CreateReadStatusDTO(
        UUID userId,
        UUID channelId,
        Instant lastReadAt
) {}
