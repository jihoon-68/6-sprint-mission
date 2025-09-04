package com.sprint.mission.discodeit.dto.User;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        boolean isOnline
) {
}
