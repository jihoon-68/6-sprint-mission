package com.sprint.mission.discodeit.dto.channel.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChannelDto {
    private UUID channelId;
    private String channelName;
    private UUID ownerId;
    private Instant messageCreateAt;
    private List<UUID> memberIds;
    private boolean isPrivate;
}
