package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

// 프사 또는 첨부파일
@Getter
public class BinaryContent implements Serializable {
    private final UUID id;
    private final UUID userId;
    private final UUID messageId; // null 허용. 프사의 경우 없을수도 있으므로
    private final BinaryContentType type;
    private final byte[] data;
    private final Instant createdAt;

    public BinaryContent(UUID userId, UUID messageId, BinaryContentType type, byte[] data) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = userId;
        this.messageId = messageId;
        this.type = type;
        this.data = data;
    }
}

