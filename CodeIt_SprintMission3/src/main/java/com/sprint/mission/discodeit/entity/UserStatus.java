package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;

    private final UUID userId;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.userId = userId;
    }

    public void update() {
        this.updatedAt = Instant.now();
    }

    public boolean isOnline() {
        if (this.updatedAt == null) {
            return false;
        }
        return this.updatedAt.isAfter(Instant.now().minus(5, ChronoUnit.MINUTES));
    }
}
