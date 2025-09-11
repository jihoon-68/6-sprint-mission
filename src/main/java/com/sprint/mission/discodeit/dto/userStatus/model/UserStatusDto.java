package com.sprint.mission.discodeit.dto.userStatus.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserStatusDto {
    private UUID id;
    private UUID userId;
    private Instant lastLogin;
    private boolean isLogin;
}
