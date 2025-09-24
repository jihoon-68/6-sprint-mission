package com.sprint.mission.discodeit.dto.userstatusdto;

import java.time.Instant;

public record UpdateUserStatus(
    Instant newLastActiveAt
) {

}
