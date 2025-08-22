package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Common implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;

    public Common() {
        this.uuid = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        updatedAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }


}
