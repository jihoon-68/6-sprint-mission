package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class BinaryContent implements Serializable {
    private UUID id;
    private Instant createAt;
    private UUID userId;
    private UUID messageId;
    private String path;
    private static final long serializableId = 1L;

    public BinaryContent(UUID userId, UUID messageId, String path) {
        id = UUID.randomUUID();
        createAt = Instant.now();
        this.userId = userId;
        this.messageId = messageId;
        this.path = path;
    }
}
