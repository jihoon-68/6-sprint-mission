package com.sprint.mission.discodeit.entity;

import java.util.UUID;

/**
 * 객체 클래스 Message
 * content: String
 * user: User
 * channel: Channel
 * type: MessageType(TEXT, VIDEO, IMAGE, FILE)
 */
public class Message extends AbstractEntity {
    private String content;
    private UUID userId;
    private UUID channelId;
    private MessageType type;

    // Constructor
    public Message(String content, UUID userId, UUID channelId, MessageType type) {
        super();
        this.content = content;
        this.userId = userId;
        this.channelId = channelId;
        this.type = type;
    }

    // Getter
    public String getContent() { return content; }
    public UUID getUser() { return userId; }
    public UUID getChannel() { return channelId; }
    public MessageType getType() { return type; }

    // MessageType enum
    public enum MessageType {
        TEXT,
        VIDEO,
        IMAGE,
        FILE
    }

    // Update
    public void update(String content) {
        this.content = content != null ? content : this.content;
        this.setUpdatedAt(System.currentTimeMillis());
    }
}
