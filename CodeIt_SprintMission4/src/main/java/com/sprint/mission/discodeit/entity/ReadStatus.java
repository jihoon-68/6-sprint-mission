package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    //
    private UUID userId;
    private UUID channelId;
    private Instant lastReadAt;

    // Jackson을 위한 기본 생성자
    public ReadStatus() {
    }

    public ReadStatus(UUID userId, UUID channelId, Instant lastReadAt) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        //
        this.userId = userId;
        this.channelId = channelId;
        this.lastReadAt = lastReadAt;
    }

    public void update(Instant newLastReadAt) {
        boolean anyValueUpdated = false;
        if (newLastReadAt != null && !newLastReadAt.equals(this.lastReadAt)) {
            this.lastReadAt = newLastReadAt;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }
}
