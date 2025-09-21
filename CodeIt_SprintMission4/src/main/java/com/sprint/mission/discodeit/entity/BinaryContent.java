package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private Instant createdAt;
    //
    private String fileName;
    private Long size;
    private String contentType;
    private byte[] bytes;

    // Jackson을 위한 기본 생성자
    public BinaryContent() {
    }

    public BinaryContent(String fileName, Long size, String contentType, byte[] bytes) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        //
        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
        this.bytes = bytes;
    }
}
