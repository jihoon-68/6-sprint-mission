package com.sprint.mission.dto.userstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserStatusCreateDto {

    private UUID userId;
    private boolean isOnline;
    private Instant lastActiveAt;
}
