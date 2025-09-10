package com.sprint.mission.discodeit.dto.messagedto;

import java.util.List;
import java.util.UUID;

public record CreateMessageDto(
        String content,
        UUID channelId,
        UUID authorId,
        List<String> imagePath
) {
    public CreateMessageDto(String content, UUID channelId, UUID authorId) {
        this(content,channelId,authorId,null);
    }
}
