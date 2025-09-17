package com.sprint.mission.discodeit.DTO.Channel;

import java.util.UUID;

public record UpdateChannelRequest(
        UUID channelId,
        String name,
        String description
) {
}
