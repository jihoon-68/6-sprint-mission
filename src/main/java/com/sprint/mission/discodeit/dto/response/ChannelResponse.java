package com.sprint.mission.discodeit.dto.response;

import com.sprint.mission.discodeit.entity.Channel;
import java.time.Instant;
import java.util.UUID;

public record ChannelResponse(
    UUID id,
    Instant createdAt,
    Instant updatedAt,
    String type,
    String name,
    String description
) {

}
