package com.sprint.mission.discodeit.dto.user;

import java.time.Instant;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String email,
        String username,
        // boolean online,
        UUID profileId
) {
}
