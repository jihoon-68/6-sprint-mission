package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public final class PublicChannelDto {

    private PublicChannelDto() {
    }

    public record Request(String channelName, String description) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String channelName,
            String description
    ) {
    }

    public record MessageInfoResponse(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String channelName,
            String description,
            Instant lastMessageAt
    ) {
    }
}
