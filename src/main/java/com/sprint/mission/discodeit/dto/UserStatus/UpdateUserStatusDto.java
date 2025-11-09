package com.sprint.mission.discodeit.DTO.UserStatus;

import com.sprint.mission.discodeit.Enum.UserStatusType;
<<<<<<< HEAD
import com.sprint.mission.discodeit.entity.UserStatus;
=======
>>>>>>> 7c7532b (박지훈 sprint3 (#2))

import java.time.Instant;
import java.util.UUID;

public record UpdateUserStatusDTO(
        UUID id,
<<<<<<< HEAD
        Instant createdAt,
        Instant updatedAt,
        UUID userId,
        Instant lastActiveAt,
        boolean online

) {
    public UpdateUserStatusDTO(UserStatus userStatus) {
        this(
                userStatus.getId(),
                userStatus.getCreatedAt(),
                userStatus.getUpdatedAt(),
                userStatus.getUserId(),
                userStatus.getLastAccessAt(),
                userStatus.getAccessType().getValue()
        );
    }

    public static UpdateUserStatusDTO getUserStatus(UUID id, Instant LastAccessAt) {
        return new UpdateUserStatusDTO(
                null,
                null,
                null,
                id,
                LastAccessAt,
                false
        );
    }
}
=======
        UUID userId,
        Instant LastAccessAt,
        UserStatusType AccessType
) {}
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
