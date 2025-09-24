package com.sprint.mission.discodeit.dto.userstatusdto;

import java.time.Instant;
import java.util.UUID;

public record CreateUserStatus(
    UUID userId,
    Instant lastActiveAt
) {

}
