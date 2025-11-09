package com.sprint.mission.discodeit.DTO.User;

import com.sprint.mission.discodeit.Enum.UserStatusType;
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
        UUID profileId,
        boolean online
) {
    public FindUserDTO(User user, UserStatus userStatus) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                userStatus.getLastAccessAt(),
                userStatus.getAccessType()
        );
    }
}
