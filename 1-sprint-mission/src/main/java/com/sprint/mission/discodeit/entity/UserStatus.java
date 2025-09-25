package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus extends Common {
    private UUID userID;
    private UUID uuid;
    private Instant lastLoginAt;

    public UserStatus(UUID userID) {
        super();
        this.userID = userID;
        this.lastLoginAt = Instant.now();
        this.uuid = UUID.randomUUID();
    }

    public boolean isLogined() {
        var diff = Duration.between(lastLoginAt, Instant.now());

        return diff.toMinutes() < 5;
    }

    public void update() {
        updatedAt = Instant.now();
    }
}
