package com.sprint.mission.discodeit.dto.UserStatus;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UpdateUserStatusDto(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        UUID userId,
        Instant lastActiveAt,
        boolean online

) {
    public UpdateUserStatusDto(UserStatus userStatus) {
        this(
                userStatus.getId(),
                userStatus.getCreatedAt(),
                userStatus.getUpdatedAt(),
                userStatus.getUser().getId(),
                userStatus.getLastAccessAt(),
                false
        );
    }

    public static UpdateUserStatusDto getUserStatus(UUID id, Instant LastAccessAt) {
        return new UpdateUserStatusDto(
                null,
                null,
                null,
                id,
                LastAccessAt,
                false
        );
    }
}
