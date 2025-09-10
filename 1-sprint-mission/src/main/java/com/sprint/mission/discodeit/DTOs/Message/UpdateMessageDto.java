package com.sprint.mission.discodeit.DTOs.Message;

import java.util.UUID;

public record UpdateMessageDto(
        UUID messageId,
        String newContent
) {
}