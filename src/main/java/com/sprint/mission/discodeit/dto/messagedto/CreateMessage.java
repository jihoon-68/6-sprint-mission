package com.sprint.mission.discodeit.dto.messagedto;

import java.util.List;
import java.util.UUID;

public record CreateMessage(
        String content,
        UUID channelId,
        UUID authorId,
        List<UUID> attachmentIds
) {
    public CreateMessage(String content, UUID channelId, UUID authorId) {
        this(content,channelId,authorId,null);
    }
}
