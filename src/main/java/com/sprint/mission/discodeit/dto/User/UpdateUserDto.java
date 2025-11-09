package com.sprint.mission.discodeit.DTO.User;

import java.util.UUID;

public record UpdateUserDTO(
        UUID id,
        String userName,
        String email,
        UUID profileId,
        String password
) {}
