package com.sprint.mission.discodeit.dto.User;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        BinaryContentDto profile,
        boolean online
) {
}
