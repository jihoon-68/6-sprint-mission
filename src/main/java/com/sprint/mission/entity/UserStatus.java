package com.sprint.mission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserStatus extends EntityCommon{

    private UUID userId;
    private boolean isOnline;
    private Instant lastActiveAt;

    public UserStatus(UUID userId, boolean isOnline, Instant lastActiveAt) {
        super();
        this.userId = userId;
        this.isOnline = isOnline;
        this.lastActiveAt = lastActiveAt;
    }
}
