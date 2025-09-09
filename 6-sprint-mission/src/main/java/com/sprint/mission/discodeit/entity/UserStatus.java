package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus {
    private UUID id;
    private UUID userid;

    public UserStatus(User user) {
        this.id = UUID.randomUUID();
        this.userid = user.getId();
    }

    public boolean isOnline(User user) {
        return (user.getUpdatedAt() - Instant.now().getEpochSecond()) <= 300;
    }
}
