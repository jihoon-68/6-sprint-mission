package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private UUID id;
    private Long updateAt;
    private Long createAt;
    private UUID autherId;
    private String content;
    private UUID channelId;

    public Message(UUID autherId, UUID channelId, String content) {
        this.autherId = autherId;
        this.channelId = channelId;
        this.content = content;
        this.createAt = System.currentTimeMillis();
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public UUID getAutherId() {
        return autherId;
    }

    public String getContent() {
        return content;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public void updateContent(String content) {
        this.content = content;
        this.updateAt = System.currentTimeMillis();
    }
}
