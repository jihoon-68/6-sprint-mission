package com.sprint.mission.discodeit.DTO.Channel;

import java.util.UUID;
public record CreatePublicChannelDTO(
        UUID userId,
        String channelName,
        String description
) {

}
