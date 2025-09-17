package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID userId;
    private UUID channelId;
    private Instant createdAt;
    private Instant updatedAt;

    public ReadStatus(UUID userId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        this.createdAt = Instant.ofEpochSecond(Instant.now().getEpochSecond());
    }

    public void update(UUID newUserId, UUID newChannelId) {
        boolean anyValueUpdated = false;
        if (newUserId != null && !newUserId.equals(this.userId)) {
            this.userId = newUserId;
            anyValueUpdated = true;
        }
        if (newChannelId != null && !newChannelId.equals(this.channelId)) {
            this.channelId = newChannelId;
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            this.updatedAt = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        }
    }

}
