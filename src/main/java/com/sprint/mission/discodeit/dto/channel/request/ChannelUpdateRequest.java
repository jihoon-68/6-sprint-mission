package com.sprint.mission.discodeit.dto.channel.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChannelUpdateRequest {
    private UUID ownerId;
    private UUID channelId;
    private String channelName;
}
