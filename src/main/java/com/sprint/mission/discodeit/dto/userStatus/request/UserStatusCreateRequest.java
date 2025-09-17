package com.sprint.mission.discodeit.dto.userStatus.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserStatusCreateRequest {
    private UUID userId;
}
