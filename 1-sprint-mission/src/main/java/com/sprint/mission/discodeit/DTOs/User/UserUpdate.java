package com.sprint.mission.discodeit.DTOs;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.Optional;
import java.util.UUID;

public record UserUpdate(
        UUID userId,
        String newUsername,
        String newEmail,
        String newPassword,
        Optional<BinaryContent> newBinaryContent) {

}
