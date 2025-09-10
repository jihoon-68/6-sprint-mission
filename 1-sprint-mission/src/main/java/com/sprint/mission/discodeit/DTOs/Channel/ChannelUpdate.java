package com.sprint.mission.discodeit.DTOs.Channel;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.Set;
import java.util.UUID;

public record ChannelUpdate(
        UUID id,
        String name,
        String description
) {
}
