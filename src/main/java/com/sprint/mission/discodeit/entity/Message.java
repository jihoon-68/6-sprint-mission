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
    private static final long serializableId = 1L;

    public Message(UUID authorId, UUID channelId, String content) {
        this.authorId = authorId;
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

    public UUID getAuthorId() {
        return authorId;
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
