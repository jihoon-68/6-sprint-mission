package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private final UUID id;
    private final UUID userId;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant lastlyConnectedAt;

    public boolean isOnline() {
        Instant fiveMinuteAgo = Instant.now().minus(5, ChronoUnit.MINUTES);
        return lastlyConnectedAt.isAfter(fiveMinuteAgo); // 5분전 시각이 마지막 접속시간보다 뒤이면 false 반환
    }

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.lastlyConnectedAt = Instant.now();
        this.createdAt = Instant.now();
    }

    public void setLastlyConnectedAt(Instant lastlyConnectedAt) {
        this.lastlyConnectedAt = lastlyConnectedAt;
        this.updatedAt = Instant.now();
    }
}