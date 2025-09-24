package com.sprint.mission.discodeit.binarycontent;

import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent.OwnerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public final class BinaryContentDto {

    private BinaryContentDto() {
    }

    public record Request(
            @NotNull OwnerType ownerType,
            @NotNull UUID ownerId,
            @NotBlank String bytesBase64
    ) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            OwnerType ownerType,
            UUID ownerId,
            String bytesBase64
    ) {
    }
}
