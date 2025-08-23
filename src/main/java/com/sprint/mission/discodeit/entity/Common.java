package com.sprint.mission.discodeit.entity;

import java.util.UUID;

/**
 * 추상 클래스 Common
 * id : UUID
 * createdAt : Long (for Unix TimeStamp)
 * updatedAt : Long (for Unix TimeStamp)
 */
public abstract class Common {
    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    // Constructor
    public Common() {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    // Getter
    public UUID getId() { return id; }
    public Long getCreatedAt() { return createdAt; }
    public Long getUpdatedAt() { return updatedAt; }

    // Setter
    public void setId(UUID id) { this.id = id; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }
}
