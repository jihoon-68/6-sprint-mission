package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class ReadStatus {
    private UUID id;
    private UUID userId;
    private UUID channelId;
    private User user;
    private Channel channel;

    public ReadStatus(UUID id, User user, Channel channel) {
        this.id = id;
        this.user = user;
        this.channel = channel;
        this.userId = user.getId();
        this.channelId = channel.getId();
    }
}
