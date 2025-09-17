package com.sprint.mission.discodeit.dto.Channel;

import com.sprint.mission.discodeit.entity.Channel;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelInfoDto(
        Instant latestTime,
        List<UUID> userIds,
        Channel channel
) {
}
