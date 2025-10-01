package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

// 프사 또는 첨부파일
@Getter
public class BinaryContent implements Serializable {
    private final UUID id;
    private final BinaryContentType type;
    private final String fileName;
    private final String extension;
    private final Long size;
    private final byte[] data;
    private final Instant createdAt;

    public BinaryContent(String name, String extension, BinaryContentType type, byte[] data, Long size) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.fileName = name;
        this.extension = extension;
        this.type = type;
        this.data = data;
        this.size = size;
    }
}

