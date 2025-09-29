
package com.sprint.mission.discodeit.dto.ReadStatus;

import com.sprint.mission.discodeit.enumtype.ReadType;

import java.util.UUID;

public record UpdateReadStatusDTO(
        UUID id,
        UUID userId,
        UUID channelId,
        ReadType type
) {}
