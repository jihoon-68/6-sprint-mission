package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private UUID id;
    private long createdAt;
    private long updatedAt;
    private String msg;
    private UUID userId; // DI를 위한 필드 추가
    private UUID channelId; // DI를 위한 필드 추가

    public Message(String msg,  UUID userId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.msg = msg;
        this.userId = userId;
        this.channelId = channelId;
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

    public UUID getUserId() { return userId; }

    public UUID getChannelId() { return channelId; }

    public void update(String newMsg) {
        this.msg = newMsg;
        this.updatedAt = System.currentTimeMillis();
    }
}
