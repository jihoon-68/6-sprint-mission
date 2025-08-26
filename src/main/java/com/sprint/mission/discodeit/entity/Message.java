package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    private UUID id;
    private Long updateAt;
    private Long createAt;
    private UUID authorId;
    private String content;
    private UUID channelId;
    private UUID receiverId;
    private static final long serializableId = 1L;

    public Message(UUID authorId, UUID channelId, UUID receiverId, String content) {
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
        this.createAt = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.receiverId = receiverId;
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

    public UUID getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public UUID getReceiverId() {return receiverId;}

    public void updateContent(String content) {
        this.content = content;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateAuthorId(UUID authorId) {
        this.authorId = authorId;
        this.updateAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", updateAt=" + updateAt +
                ", createAt=" + createAt +
                ", authorId=" + authorId +
                ", content='" + content + '\'' +
                ", channelId=" + channelId +
                ", receiverId=" + receiverId +
                '}';
    }
}
