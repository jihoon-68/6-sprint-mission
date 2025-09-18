package com.sprint.mission.discodeit.DTO.Channel;

import com.sprint.mission.discodeit.Enum.ChannelType;

public record CreatePublicChannelDTO(
        String channelName,
        String description,
        ChannelType channelType
) {

}
