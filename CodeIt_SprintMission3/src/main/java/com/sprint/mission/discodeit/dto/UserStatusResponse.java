package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.time.Instant;
import java.util.UUID;

public record UserStatusResponse(
        UUID id,
        UUID userId,
        Instant createdAt,
        Instant updatedAt,
        boolean isOnline
) {
    public static UserStatusResponse of(UserStatus userStatus) {
        return new UserStatusResponse(
                userStatus.getId(),
                userStatus.getUserId(),
                userStatus.getCreatedAt(),
                userStatus.getUpdatedAt(),
                userStatus.isOnline()
        );
    }
}
