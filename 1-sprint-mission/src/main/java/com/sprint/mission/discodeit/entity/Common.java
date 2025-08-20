package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;

public abstract class Common {
    private String id;
    protected Long createAt;
    protected Long updatedAt;

    public Common()
    {
        id = UUID.randomUUID().toString();
        long time =  Instant.now().getEpochSecond();
        createAt = time;
        updatedAt = time;
    }

    public String getId() {
        return id;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void updatedAt() {
        updatedAt = Instant.now().getEpochSecond();
    }
}
