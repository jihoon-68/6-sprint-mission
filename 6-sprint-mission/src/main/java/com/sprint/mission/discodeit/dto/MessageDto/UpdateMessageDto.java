package com.sprint.mission.discodeit.dto.MessageDto;

import java.util.UUID;

public record UpdateMessageDto(
        UUID messageId,
        String newContent
) {
}
