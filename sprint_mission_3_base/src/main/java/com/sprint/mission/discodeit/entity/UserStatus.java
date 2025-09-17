package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.createdAt = Instant.ofEpochSecond(Instant.now().getEpochSecond());
    }

    public boolean isActive() {
        if(updatedAt == null) {
            return false;
        }
        if(Duration.between(updatedAt, Instant.now()).toMinutes() <= 5) {
            return true;
        }
        return false;
    }

    public void update() {
        this.updatedAt = Instant.ofEpochSecond(Instant.now().getEpochSecond());
    }
}
