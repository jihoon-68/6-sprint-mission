package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus {

    private UUID id;
    private UUID userId;
    private Instant recentOnline;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.recentOnline = Instant.now();
    }

    public void updateOnline() {
        this.recentOnline = Instant.now();
    }

    public boolean isOnline() {
        if(recentOnline == null) {
            return false;
        }
        return recentOnline.isAfter(Instant.now().minusSeconds(60*5));
    }

}
