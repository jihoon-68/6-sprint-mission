package com.sprint.mission.discodeit.dto.User;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record FindUserDTO(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String username,
        String email,
        BinaryContent profileId,
        boolean online
) {
    public FindUserDTO(User user, UserStatus userStatus) {
        this(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getProfile(),
                userStatus.isConnecting(Instant.now())
        );
    }
}
