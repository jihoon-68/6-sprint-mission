package com.sprint.mission.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class EntityCommon {
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;

    public EntityCommon() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

}
