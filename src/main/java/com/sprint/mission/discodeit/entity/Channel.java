package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Channel implements Serializable {
    private final UUID channelId;
    private final Long createdAt;
    private Long updatedAt;
    private String channelName;
    private final UUID ownerId;

    //생성자
    public Channel(String channelName, UUID ownerId) {
        this.channelId = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = null;
        this.channelName = channelName;
        this.ownerId = ownerId;
    }

    //getter
    public UUID getChannelId() {
        return channelId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getChannelName() {
        return channelName;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    //updateMethod
    public void updateChannel(String channelName) {
        this.channelName = channelName;
        this.updatedAt = System.currentTimeMillis();
    }
}
