package com.sprint.mission.discodeit.dto.User;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;

import java.time.Instant;
import java.util.UUID;

public record UpdateUserResponse(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String username,
        String email,
        String password,
        BinaryContent profileId
) {
    public UpdateUserResponse(User user) {
        this(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getProfile()
        );
    }
}
