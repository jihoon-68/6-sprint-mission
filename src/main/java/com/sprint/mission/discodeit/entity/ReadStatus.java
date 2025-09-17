package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

//Channel별 마지막 들어간 시간
@Getter
public class ReadStatus implements Serializable {
    private UUID id;
    private UUID userId;
    private Instant updateAt;
    private Instant createAt;
    private Instant readAt;
    private UUID channelId;
    private static final long serializableId = 1L;

    public ReadStatus(UUID userId, UUID channelId) {
        this.createAt = Instant.now();
        id =  UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
    }

    public void updateReadAt() {
        this.readAt = Instant.now();
        updateAt = Instant.now();
    }
}
