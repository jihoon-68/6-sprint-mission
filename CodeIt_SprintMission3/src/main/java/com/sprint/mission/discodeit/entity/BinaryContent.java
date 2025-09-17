package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Instant createdAt;

    // TODO: 바이너리 데이터를 저장하기 위한 필드를 추가해야 합니다.
    // 예: private byte[] data;
    // 예: private String fileUrl;

    public BinaryContent(/* ... */) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }
}
