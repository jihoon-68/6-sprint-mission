package com.sprint.mission.discodeit.DTO.Message;

import java.util.UUID;

public record UpdateMessageRequest(
        UUID messageId,
        String content
) {
}
