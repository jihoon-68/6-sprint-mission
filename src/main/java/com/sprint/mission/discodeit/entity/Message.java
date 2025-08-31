package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    private final UUID messageId;
    private final Long createdAt;
    private Long updatedAt;
    private String content;
    private final UUID userID;
    private final UUID channelId;

    //생성자
    public Message(String content, UUID userID, UUID channelId) {
        this.messageId = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = null;
        this.content = content;
        this.userID = userID;
        this.channelId = channelId;
    }

    //getter
    public UUID getmessageId() {
        return this.messageId;
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public Long getUpdatedAt() {
        return this.updatedAt;
    }

    public String getContent() {
        return this.content;
    }

    public UUID getUserId() {
        return this.userID;
    }

    public UUID getChannelId() {
        return this.channelId;
    }

    //update Method
    public void updateMessage(String content) {
        this.updatedAt = System.currentTimeMillis();
        this.content = content;
    }

}
