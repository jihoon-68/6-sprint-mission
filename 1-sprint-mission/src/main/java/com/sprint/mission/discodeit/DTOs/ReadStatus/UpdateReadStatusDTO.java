package com.sprint.mission.discodeit.DTOs.ReadStatus;

import java.util.UUID;

public record UpdateReadStatusDTO(
        UUID id,
        UUID userId,
        UUID channelId
) {
}
