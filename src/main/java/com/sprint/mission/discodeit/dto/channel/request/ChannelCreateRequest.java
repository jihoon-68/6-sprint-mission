package com.sprint.mission.discodeit.dto.channel.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChannelCreateRequest {
    private String channelName;
    private UUID ownerId;
    private boolean isPrivate;
}
