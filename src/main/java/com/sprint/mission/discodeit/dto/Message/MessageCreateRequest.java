package com.sprint.mission.discodeit.dto.Message;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MessageCreateRequest (
        UUID channelId,
        UUID authorId,
        String content
){
}
