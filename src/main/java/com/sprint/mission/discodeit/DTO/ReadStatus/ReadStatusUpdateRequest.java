package com.sprint.mission.discodeit.DTO.ReadStatus;

import java.time.Instant;

public record ReadStatusUpdateRequest(
        Instant newLastReadAt
) {
}
