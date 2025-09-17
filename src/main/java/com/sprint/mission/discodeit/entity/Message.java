package com.sprint.mission.discodeit.entity;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.UUID;

public class Message extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    private static final String MESSAGE_CONTENT_CANNOT_BE_BLANK = "Message content cannot be blank.";

    private final String content;
    private final UUID channelId;
    private final UUID authorId;

    public Message(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            String content,
            UUID channelId,
            UUID authorId
    ) {
        super(id, createdAt, updatedAt);
        if (content.isBlank()) {
            throw new IllegalArgumentException(MESSAGE_CONTENT_CANNOT_BE_BLANK);
        }
        this.content = content.trim();
        this.channelId = channelId;
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", channelId=" + channelId +
                ", authorId=" + authorId +
                "} " + super.toString();
    }
}
