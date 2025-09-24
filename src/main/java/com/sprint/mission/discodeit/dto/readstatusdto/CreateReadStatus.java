package com.sprint.mission.discodeit.dto.readstatusdto;

import java.util.UUID;

public record CreateReadStatus(
        UUID readStatusId,
        UUID userId,
        UUID channelId
) {
}
