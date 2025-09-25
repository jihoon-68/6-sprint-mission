package com.sprint.mission.discodeit.DTOs.Channel;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public record ChannelView(
        UUID id,
        ChannelType type,
        String name,                 // PRIVATE이면 null 가능
        String description,          // PRIVATE이면 null 가능
        Optional<Instant> latestMessageAt,
        Set<UUID> participantUserIds // PUBLIC이면 빈 리스트 권장
) {
}
