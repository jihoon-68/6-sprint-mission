package com.sprint.mission.discodeit.dto.userdto;

import com.sprint.mission.discodeit.entity.User;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        Long createdAt,
        Long updatedAt,
        String username,
        String email,
        UUID profileId,
        boolean online
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                user.isOnline()
        );
    }
}