package com.sprint.mission.discodeit.DTO.Channel;

import java.util.UUID;

public record UpdateChannelDTO(
        UUID id,
        String name,
        String description
) {}
