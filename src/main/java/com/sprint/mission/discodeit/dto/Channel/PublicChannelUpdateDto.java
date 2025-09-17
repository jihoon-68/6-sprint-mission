package com.sprint.mission.discodeit.dto.Channel;

import java.util.UUID;

public record PublicChannelUpdateDto(
        UUID channelID,
        String name,
        String description
) {
}
