package com.sprint.mission.discodeit.DTO.Message;

import java.util.List;
import java.util.UUID;

public record CreateMessageRequest(
        String content,
        UUID channelId,
        UUID authorId,
        List<String> attachments
) {
}
