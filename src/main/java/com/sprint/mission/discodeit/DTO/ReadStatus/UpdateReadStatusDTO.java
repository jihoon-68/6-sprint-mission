
package com.sprint.mission.discodeit.DTO.ReadStatus;

import com.sprint.mission.discodeit.Enum.ReadType;

import java.util.UUID;

public record UpdateReadStatusDTO(
        UUID id,
        UUID userId,
        UUID channelId,
        ReadType type
) {}
