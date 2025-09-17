package com.sprint.mission.discodeit.dto.channeldto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.UUID;

public record FindChannelDto(
        ChannelType type,
        UUID channelId,
        UUID userId,                    // PRIVATE 채널만 필요
        Instant latestMessageTime
) {
}
