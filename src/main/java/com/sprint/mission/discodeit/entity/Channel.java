package com.sprint.mission.discodeit.entity;

import java.util.List;
import java.util.UUID;

public class Channel {
    private UUID id;
    private Long updateAt;
    private Long createAt;
    private String channelName;
    private UUID ownerId;

    public Channel(String channelName, UUID ownerId) {
        this.channelName = channelName;
        this.ownerId = ownerId;
        this.id = UUID.randomUUID();
        this.createAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public String getChannelName() {
        return channelName;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void updateChannelName(String channelName) {
        this.channelName = channelName;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        this.updateAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                ", channelName='" + channelName + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
