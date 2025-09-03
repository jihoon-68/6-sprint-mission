package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class UserStatus {
    private UUID id;
    private UUID userid;
    private User user;

    public UserStatus(UUID id, User user) {
        this.id = id;
        this.user = user;
        this.userid = user.getId();
    }
}
