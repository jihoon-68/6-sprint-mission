package com.sprint.mission.discodeit.DTO.Channel;

import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.entity.Channel;

import java.time.Instant;

import java.util.UUID;

public record FindChannelDTO(
        Channel channel,
        Instant recentMessage,
        UUID userId
) {
    static public FindChannelDTO createPublicChannelDto(Channel channel, Instant recentMessage) {
        return new FindChannelDTO(
                channel,
                recentMessage,
                null
        );
    }
}
