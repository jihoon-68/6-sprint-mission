package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class MessageDto {

    private MessageDto() {
    }

    public record Request(
            String content,
            UUID channelId,
            UUID authorId,
            List<byte[]> binaries
    ) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String content,
            UUID channelId,
            UUID authorId
    ) {
    }
}
