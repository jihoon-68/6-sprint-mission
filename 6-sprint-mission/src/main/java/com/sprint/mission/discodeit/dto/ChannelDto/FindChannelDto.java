package com.sprint.mission.discodeit.dto.ChannelDto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.UUID;

public record FindChannelDto(
        ChannelType type,
        UUID channelId,
        UUID userId,                    //private
        Instant latestMessageTime
) {
}
