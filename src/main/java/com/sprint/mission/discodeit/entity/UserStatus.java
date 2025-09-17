package com.sprint.mission.discodeit.entity;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class UserStatus extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Duration ACTIVE_TIMEOUT_IN_MINUTES = Duration.ofMinutes(5);

    private final Instant lastActivatedAt;
    private final UUID userId;

    public UserStatus(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            Instant lastActivatedAt,
            UUID userId
    ) {
        super(id, createdAt, updatedAt);
        this.lastActivatedAt = lastActivatedAt;
        this.userId = userId;
    }

    public boolean isActive(Instant now) {
        Duration elapsed = Duration.between(lastActivatedAt, now);
        return elapsed.compareTo(ACTIVE_TIMEOUT_IN_MINUTES) < 0;
    }

    public Instant getLastActivatedAt() {
        return lastActivatedAt;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "lastActivatedAt=" + lastActivatedAt +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
