package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

public class Message extends Common implements Serializable {

    private UUID toUserId;
    private UUID ownerUserId;
    private UUID channelId;
    private String message;

    public Message(String message, UUID ownerUserId, UUID toUserId, UUID channelId) {
        super();
        this.message = message;
        this.ownerUserId = ownerUserId;
        this.toUserId = toUserId;
        this.channelId = channelId;
    }

    public String getMessage() {
        return message;
    }

    public void updateMessage(String message) {
        this.message = message;
    }

    public UUID getToUserId() {
        return toUserId;
    }

    public UUID getOwnerUserId() {
        return ownerUserId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", id=" + id +
                ", createAt=" + createAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
