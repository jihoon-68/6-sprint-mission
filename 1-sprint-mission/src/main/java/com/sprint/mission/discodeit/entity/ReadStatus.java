package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus extends Common {
    private Instant lastReadTime;
    private UUID userID;
    private UUID channelID;
    private UUID StatusID;

    public ReadStatus(Instant lastReadTime, UUID userID, UUID channelID, UUID StatusID) {
        super();
        this.lastReadTime = lastReadTime;
        this.userID = userID;
        this.StatusID = StatusID;
        this.channelID = channelID;
    }

    public ReadStatus(UUID userID, UUID channelUUID) {
        this(Instant.now(), userID, channelUUID, UUID.randomUUID());
    }

    public void update(UUID newUserId, UUID newChannelId) {
        boolean anyValueUpdated = false;
        if (newUserId != null && !newUserId.equals(this.userID)) {
            this.userID = newUserId;
            anyValueUpdated = true;
        }

        if (newChannelId != null && !newChannelId.equals(this.channelID)) {
            this.channelID = newChannelId;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }
}
