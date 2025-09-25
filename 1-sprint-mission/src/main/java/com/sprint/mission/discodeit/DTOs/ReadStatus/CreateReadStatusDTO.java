package com.sprint.mission.discodeit.DTOs.ReadStatus;

import java.util.UUID;

public record CreateReadStatusDTO(
        UUID userId,
        UUID channelId
) {
}
