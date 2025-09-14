package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.User;
import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        Instant createdAt,
        Instant updatedAt,
        UUID profileId,
        boolean isOnline
) {
    public static UserResponse of(User user, boolean isOnline) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getProfileId(),
                isOnline
        );
    }
}
