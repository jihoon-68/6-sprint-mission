package com.sprint.mission.discodeit.DTOs.User;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.Optional;
import java.util.UUID;

public record UserFind(
        UUID userID,
        String username,
        String email,
        UserStatus status,
        Optional<BinaryContent> profileImage
) {
}
