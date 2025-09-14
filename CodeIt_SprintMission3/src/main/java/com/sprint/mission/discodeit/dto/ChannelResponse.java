package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponse(
        UUID id,
        ChannelType type,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt,
        Instant lastMessageTimestamp,
        List<UUID> participantIds // For PRIVATE channels
) {
    public static ChannelResponse of(Channel channel, Instant lastMessageTimestamp, List<UUID> participantIds) {
        return new ChannelResponse(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                lastMessageTimestamp,
                participantIds
        );
    }
}
