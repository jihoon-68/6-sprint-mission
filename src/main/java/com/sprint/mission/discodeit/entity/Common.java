package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;

public abstract class Common {
    protected UUID id;
    protected Long createAt;
    protected Long updatedAt;

    public Common()
    {
        id = UUID.randomUUID();
        long time =  Instant.now().getEpochSecond();
        createAt = time;
        updatedAt = time;
    }

    public UUID getId() {
        return id;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void updateUpdatedAt()
    {
        updatedAt = Instant.now().getEpochSecond();
    }

}
