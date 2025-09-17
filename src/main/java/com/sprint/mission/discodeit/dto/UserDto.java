package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public final class UserDto {

    private UserDto() {
    }

    public record Request(
            String name,
            String mail,
            String nickname,
            String password,
            byte[] profileImage
    ) {
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

    public record OnlineInfoResponse(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String name,
            String mail,
            String nickname,
            byte[] profileImage,
            Boolean online
    ) {
    }
}
