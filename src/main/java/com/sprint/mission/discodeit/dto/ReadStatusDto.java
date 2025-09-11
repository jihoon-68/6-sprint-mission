package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public final class ReadStatusDto {

    private ReadStatusDto() {
    }

    public record Request(UUID userId, UUID channelId) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            Instant lastReadAt,
            UUID userId,
            UUID channelId
    ) {
    }
}
