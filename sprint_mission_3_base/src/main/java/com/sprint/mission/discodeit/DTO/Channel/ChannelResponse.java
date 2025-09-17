package com.sprint.mission.discodeit.DTO.Channel;

import com.sprint.mission.discodeit.entity.Channel;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponse(
        Instant latestTime,
        List<UUID> userIds,
        Channel channel

) {
}
