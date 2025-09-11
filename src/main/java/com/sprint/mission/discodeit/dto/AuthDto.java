package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public final class AuthDto {

    private AuthDto() {
    }

    public record Request(String nickname, String password) {
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String name,
            String mail,
            String nickname
    ) {
    }
}
