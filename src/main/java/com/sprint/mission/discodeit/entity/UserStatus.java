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
        this.userId = userId;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }

    public boolean isOnlineUser() {
        if(updatedAt == null) {
            return false;
        }

        if(Duration.between(updatedAt, Instant.now()).compareTo(Duration.ofSeconds(300)) <= 0) {
            return true;
        }

        return false;
    }

    public void update()
    {
        updatedAt = Instant.now();
    }
}
