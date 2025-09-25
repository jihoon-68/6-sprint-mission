package com.sprint.mission.discodeit.DTO.Channel;

import java.util.List;
import java.util.UUID;

public record CreatePrivateChannelDTO(
        List<UUID> participantIds
) {}
