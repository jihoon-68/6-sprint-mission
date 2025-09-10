package com.sprint.mission.discodeit.dto.userstatusdto;

import java.util.UUID;

public record UserStatusDto(
        UUID userStatusDtoId,
        UUID userId
) {
}
