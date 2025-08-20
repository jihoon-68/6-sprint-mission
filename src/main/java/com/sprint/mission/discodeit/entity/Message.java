package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String msg;

    public Message(String msg) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void update(String newMsg) {
        this.msg = newMsg;
        this.updatedAt = System.currentTimeMillis();
    }
}
