package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserStatus extends Common{
    private UUID userId;
    private boolean userStatus;

    public UserStatus(UUID userId) {
        this.userId = userId;
        userStatus = true;
    }
}
