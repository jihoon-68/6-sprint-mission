package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User {
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String name;

    public User(String name) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void update(String newName) {
        this.name = newName;
        this.updatedAt = System.currentTimeMillis();
    }
}
