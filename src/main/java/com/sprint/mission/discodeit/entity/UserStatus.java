package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;
}
