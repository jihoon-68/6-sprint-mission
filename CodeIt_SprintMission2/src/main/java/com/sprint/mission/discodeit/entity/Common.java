package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id; // 변경되면 안됨
    private final Long createdAt; // 변경되면 안됨
    private Long updateAt;

    public Common(){
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updateAt = null;

    }

    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public Long setUpdatedAt(){
        return this.updateAt = System.currentTimeMillis();
    }


}
