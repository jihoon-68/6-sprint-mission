package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Message(
        UUID id,
        Long createdAt,
        Long updatedAt,
        String content,
        UUID channelId,
        UUID authorId
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public Message(
            UUID id,
            Long createdAt,
            Long updatedAt,
            String content,
            UUID channelId,
            UUID authorId
    ) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: 'null'");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Invalid content: '%s'".formatted(content));
        }
        if (channelId == null) {
            throw new IllegalArgumentException("Invalid channelId: 'null'");
        }
        if (authorId == null) {
            throw new IllegalArgumentException("Invalid authorId: 'null'");
        }
        this.id = id;
        this.createdAt = Objects.requireNonNullElseGet(createdAt, () -> Instant.now().toEpochMilli());
        this.updatedAt = Objects.requireNonNullElse(updatedAt, createdAt);
        this.content = content.trim();
        this.channelId = channelId;
        this.authorId = authorId;
    }

    public static Message of(String content, UUID channelId, UUID authorId) {
        long now = Instant.now().toEpochMilli();
        return new Message(
                UUID.randomUUID(),
                now,
                now,
                content,
                channelId,
                authorId
        );
    }

    public Message updateContent(String newContent) {
        return new Message(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                newContent,
                channelId,
                authorId
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message message)) return false;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
