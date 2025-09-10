package com.sprint.mission.discodeit.dto.readstatusdto;

import java.util.UUID;

public record CreateReadStatusDto(
        UUID readStatusId,
        UUID userId,
        UUID channelId
) {
}
