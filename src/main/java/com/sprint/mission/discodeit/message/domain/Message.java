package com.sprint.mission.discodeit.message.domain;

import com.sprint.mission.discodeit.common.persistence.BaseEntity;
import com.sprint.mission.discodeit.message.domain.MessageException.BlankMessageContentException;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.UUID;

public class Message extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2L;

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
            throw new BlankMessageContentException();
        }
        this.content = content.trim();
        this.channelId = channelId;
        this.authorId = authorId;
    }

    public static Message of(String content, UUID channelId, UUID authorId) {
        return new Message(
                null,
                null,
                null,
                content,
                channelId,
                authorId
        );
    }

    public Message with(String content) {
        return new Message(
                this.getId(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                content,
                this.channelId,
                this.authorId
        );
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
