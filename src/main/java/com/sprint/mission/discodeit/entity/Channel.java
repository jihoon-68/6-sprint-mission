package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
public class Channel implements Serializable {
    private UUID id;
    private Long updateAt;
    private Long createAt;
    private String channelName;
    private UUID ownerId;
    private boolean isPrivate;
    private List<UUID> memberIds;
    private static final long serializableId = 1L;

    public Channel(String channelName, UUID ownerId, boolean isPrivate) {
        this.channelName = channelName;
        this.ownerId = ownerId;
        this.id = UUID.randomUUID();
        this.createAt = System.currentTimeMillis();
        this.isPrivate = isPrivate;
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
