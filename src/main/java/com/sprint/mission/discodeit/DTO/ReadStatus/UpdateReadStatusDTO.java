
package com.sprint.mission.discodeit.DTO.ReadStatus;

import com.sprint.mission.discodeit.Enum.ReadType;

import java.util.UUID;

public record UpdateReadStatusDTO(
        UUID id,
        UUID userId,
        UUID channelId,
<<<<<<< HEAD
        ReadType type
=======
        ReadType readStatus
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
) {}
