package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class Common {
    private UUID id;
    protected Instant createdAt;
    protected Instant updatedAt;

    public Common()
    {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    protected void refreshUpdatedAt() {
        updatedAt = Instant.now();
    }
}
