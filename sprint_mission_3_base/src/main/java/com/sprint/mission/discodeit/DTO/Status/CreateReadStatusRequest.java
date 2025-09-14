package com.sprint.mission.discodeit.DTO.Status;

import java.util.UUID;

public record CreateReadStatusRequest(
        UUID userId,
        UUID channelId
) {
}
