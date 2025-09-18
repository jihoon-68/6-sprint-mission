package com.sprint.mission.discodeit.DTO.User;

import com.sprint.mission.discodeit.Enum.UserStatusType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record FindUserDTO(
        UUID id,
        String name,
        String email,
        Integer age,
        UUID profileId,
        Instant lastAccessAt,
        UserStatusType userStatus
) {
    public FindUserDTO(User user, UserStatus userStatus) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAge(),
                user.getProfileId(),
                userStatus.getLastAccessAt(),
                userStatus.getAccessType()
        );
    }
}
