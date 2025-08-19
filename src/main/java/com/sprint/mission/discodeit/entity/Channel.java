package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Channel {
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String ChName;

    public Channel(String ChName) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.ChName = ChName;
    }

    public UUID getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getChName() {
        return ChName;
    }

    public void update(String newChName) {
        this.ChName = newChName;
        this.updatedAt = System.currentTimeMillis();
    }
}
