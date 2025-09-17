package com.sprint.mission.discodeit.DTO.User;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID userid,
        String username,
        String email,
        String password,
        String attachments
) {
}
