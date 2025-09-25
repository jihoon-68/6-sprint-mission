package com.sprint.mission.discodeit.DTO.ReadStatus;

import com.sprint.mission.discodeit.Enum.ReadType;

import java.time.Instant;
import java.util.UUID;

public record CreateReadStatusDTO(
        UUID userId,
        UUID channelId,
        Instant lastReadAt
) {}
