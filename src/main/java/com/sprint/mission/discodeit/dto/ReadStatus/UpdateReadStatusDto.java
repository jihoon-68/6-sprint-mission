package com.sprint.mission.discodeit.dto.ReadStatus;

import java.util.UUID;

public record UpdateReadStatusDto(
        UUID id,
        UUID userId,
        UUID channelId
) {
}
