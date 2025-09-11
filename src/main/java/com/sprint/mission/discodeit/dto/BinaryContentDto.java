package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent.OwnerType;

import java.time.Instant;
import java.util.UUID;

public final class BinaryContentDto {

    private BinaryContentDto() {
    }

    public record Request(OwnerType ownerType, UUID ownerId, byte[] data) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            OwnerType ownerType,
            UUID ownerId
    ) {
    }
}
