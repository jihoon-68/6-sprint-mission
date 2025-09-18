package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentDTO;
import com.sprint.mission.discodeit.DTO.BinaryContent.CreateBinaryContentUserDTO;
import lombok.Getter;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant createdAt;
    private final UUID userId;
    private final UUID channelId;
    private final String filePath;

    //채널에 파일 업로드
    public BinaryContent(UUID userId, UUID channelId, String filePath) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = userId;
        this.channelId = channelId;
        this.filePath = filePath;
    }

    //유저 프로필사진 업로드
    public BinaryContent(UUID userId, String filePath) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = userId;
        this.channelId = null;
        this.filePath = filePath;
    }
}
