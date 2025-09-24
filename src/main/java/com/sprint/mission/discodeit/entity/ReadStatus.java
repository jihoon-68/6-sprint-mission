package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.Enum.ReadType;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;

    private final UUID userId;
    private final UUID channelId;
    private ReadType type;
    private Instant lastReadAt ;

    public ReadStatus(UUID channelId, UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;

        this.type = ReadType.UNREAD;
        this.createdAt = Instant.now();
        this.lastReadAt = Instant.now();
    }

    public void update(Instant lastReadAt) {
        boolean anyValueUpdated = false;
        if (lastReadAt != null && !lastReadAt.equals(this.lastReadAt)) {
            this.lastReadAt = lastReadAt;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }

    public void messageRead(Instant lastReadAt){
        this.lastReadAt = lastReadAt;
        this.updatedAt = Instant.now();
        this.type = ReadType.READ;
    }
}
