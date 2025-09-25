package com.sprint.mission.discodeit.dto.readstatus;

import java.time.Instant;
import java.util.UUID;

public record CreateReadStatusRequest(
    Instant lastReadAt,
    UUID userId,
    UUID channelId
) {

}
