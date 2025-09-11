package com.sprint.mission.discodeit.DTO.Channel;

import com.sprint.mission.discodeit.entity.Channel;

import java.time.Instant;

import java.util.UUID;

public record FindChannelDTO(
        Channel channel,
        Instant recentMessage,
        UUID userId
) {}
