package com.sprint.mission.discodeit.dto.User;

import java.util.UUID;

public record UpdateUserDto(
        UUID id,
        String userName,
        String email,
        UUID profileId,
        String password
) {}
