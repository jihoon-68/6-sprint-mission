package com.sprint.mission.discodeit.DTO.UserStatus;

import java.time.Instant;

public record UserStatusUpdateRequest(
        Instant newLastActiveAt
) {
}
