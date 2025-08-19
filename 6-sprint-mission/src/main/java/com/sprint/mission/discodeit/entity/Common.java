package com.sprint.mission.discodeit.entity;
import java.util.UUID;

public class Common {
    private UUID id;
    private Long createAt, updateAt;

    public Common() {
        this.id = UUID.randomUUID();
        this.createAt = System.currentTimeMillis();
        this.updateAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }
}
