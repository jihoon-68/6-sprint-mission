package com.sprint.mission.discodeit.DTOs.Message;

import java.util.List;
import java.util.UUID;

public record MessageCreateRequest(
        String content,
        UUID channelId,
        UUID authorId,
        List<String> attachments
) {
}
