package com.sprint.mission.discodeit.dto.userstatusdto;

import java.util.UUID;

public record CreateUserStatus(
        UUID userStatusDtoId,
        UUID userId
) {
}
