package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentDTO;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant createdAt;
    private final UUID userId;
    private final UUID channelId;
    private final byte[] content;

    public BinaryContent(CreateBinaryContentDTO createBinaryContentDTO) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = createBinaryContentDTO.userId();
        this.channelId = createBinaryContentDTO.channelID();
        this.content = createBinaryContentDTO.content();
    }
}
