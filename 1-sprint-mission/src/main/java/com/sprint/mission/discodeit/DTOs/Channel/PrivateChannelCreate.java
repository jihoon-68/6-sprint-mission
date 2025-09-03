package com.sprint.mission.discodeit.DTOs.Channel;

import java.util.Set;
import java.util.UUID;

public record PrivateChannel(
        UUID creatorId,
        Set<UUID> participantIds)
{

}
