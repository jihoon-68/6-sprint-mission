package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Common {
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;

    public Common(UUID uuid, Long createdAt) {
        this.uuid = uuid;
        this.createdAt = createdAt;
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

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

}
