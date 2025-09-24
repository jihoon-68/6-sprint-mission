package com.sprint.mission.discodeit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public final class UserDto {

    private UserDto() {
    }

    public record Request(
            @NotBlank String name,
            @NotNull @Email String mail,
            @NotBlank String nickname,
            @NotBlank String password,
            @Nullable String profileImageBase64
    ) {
        public Request {
            if (profileImageBase64 == null) {
                profileImageBase64 = "";
            }
        }
    }

    public record RequestWithLastActivateAt(@NotNull Instant lastActivatedAt) {
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

    public record ResponseWithOnline(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String name,
            String mail,
            String nickname,
            @Nullable UUID userProfileId,
            Boolean online
    ) {
    }

    public record ResponseWithLastActivatedAt(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String name,
            String mail,
            String nickname,
            Instant lastActivatedAt
    ) {
    }
}
