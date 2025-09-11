package com.sprint.mission.discodeit.dto.user;

import java.util.UUID;

public record UserResponseDto(
    UUID id,
    String email,
    String username,
    boolean isOnline
) {
}
