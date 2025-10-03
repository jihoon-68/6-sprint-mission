package com.sprint.mission.discodeit.dto.user;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
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
