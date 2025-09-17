package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public class Message implements Serializable {
    private UUID id;
    private Instant updateAt;
    private Instant createAt;
    private UUID authorId;
    private String content;
    private UUID channelId;
    private UUID receiverId;
    private boolean isDrawnAuthor;
    private boolean isDrawnReceiver;
    private static final long serializableId = 1L;

    public Message(UUID authorId, UUID channelId, UUID receiverId, String content, boolean isDrawnReceiver) {
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
        this.createAt = Instant.now();
        this.id = UUID.randomUUID();
        this.receiverId = receiverId;
        this.isDrawnAuthor = false;
        this.isDrawnReceiver = isDrawnReceiver;
    }

    public void updateContent(String content) {
        this.content = content;
        this.updateAt = Instant.now();
    }

    public void updateIsDrawnAuthor(boolean isDrawnAuthor) {
        this.isDrawnAuthor = isDrawnAuthor;
        this.updateAt = Instant.now();
    }

    public void updateIsDrawnReceiver(boolean isDrawnReceiver) {
        this.isDrawnReceiver = isDrawnReceiver;
        this.updateAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Message{" +
                "\nauthorId=" + authorId +
                ",\ncontent='" + content + '\'' +
                ",\nchannelId=" + channelId +
                ",\nreceiverId=" + receiverId +
                ",\nisDrawnAuthor=" + isDrawnAuthor +
                ",\nisDrawnReceiver=" + isDrawnReceiver +
                "\n}";
    }
}
