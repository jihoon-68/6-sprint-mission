package com.sprint.mission.discodeit.DTO.UserStatus;

import com.sprint.mission.discodeit.Enum.UserStatusType;

import java.time.Instant;
import java.util.UUID;

public record UpdateUserStatusDTO(
        UUID id,
        UUID userId,
        Instant LastAccessAt,
        UserStatusType AccessType
) {}
