package com.sprint.mission.discodeit.entity;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.UUID;

public class ReadStatus extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Instant lastReadAt;
    private final UUID userId;
    private final UUID channelId;

    public ReadStatus(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            Instant lastReadAt,
            UUID userId,
            UUID channelId
    ) {
        super(id, createdAt, updatedAt);
        this.lastReadAt = lastReadAt;
        this.userId = userId;
        this.channelId = channelId;
    }

    public Instant getLastReadAt() {
        return lastReadAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return "ReadStatus{" +
                "lastReadAt=" + lastReadAt +
                ", userId=" + userId +
                ", channelId=" + channelId +
                "} " + super.toString();
    }
}
