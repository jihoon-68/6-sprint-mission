package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    /// 수정 불가이기에 final로 선언
    private final UUID ID;
    private final UUID USER_ID;
    private final UUID MESSAGE_ID;
    private final String FILE_NAME;
    private final byte[] CONTENT;

    public BinaryContent(UUID id, UUID userId, UUID messageId, String fileName, byte[] content) {
        this.ID = UUID.randomUUID();
        this.USER_ID = userId;
        this.MESSAGE_ID = messageId;
        this.FILE_NAME = fileName;
        this.CONTENT = content.clone();
    }
}
