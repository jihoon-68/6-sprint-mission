package com.sprint.mission.discodeit.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.UUID;

public final class AuthDto {

    private AuthDto() {
    }

    public record Request(@NotBlank String nickname, @NotEmpty String password) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String nickname,
            Instant lastActivatedAt
    ) {
    }
}
