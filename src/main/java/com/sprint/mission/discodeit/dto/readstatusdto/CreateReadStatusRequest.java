package com.sprint.mission.discodeit.dto.readstatusdto;

import java.time.Instant;
import java.util.UUID;

public record CreateReadStatusRequest(
    Instant lastReadAt,
    UUID userId,
    UUID channelId
) {

}
