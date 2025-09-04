package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * 추상 클래스 AbstractEntity
 * id : UUID
 * createdAt : Long (for Unix TimeStamp)
 * updatedAt : Long (for Unix TimeStamp)
 */
public abstract class AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    // Constructor
    protected AbstractEntity() {
        this.id = UUID.randomUUID();
        long time = System.currentTimeMillis();
        this.createdAt = time;
        this.updatedAt = time;
    }

    // Getter
    public UUID getId() { return id; }
    public Long getCreatedAt() { return createdAt; }
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
}
