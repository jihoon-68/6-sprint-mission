package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public final class UserStatusDto {

    private UserStatusDto() {
    }

    public record Request(UUID userId, Instant lastActivatedAt) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            Instant lastActivatedAt,
            UUID userId
    ) {
    }
}
