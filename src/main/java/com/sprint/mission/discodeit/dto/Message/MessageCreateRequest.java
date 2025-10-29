package com.sprint.mission.discodeit.dto.Message;

import java.util.UUID;

public record MessageCreateRequest (
        UUID channelId,
        UUID authorId,
        String content
){
}
