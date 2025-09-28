package com.sprint.mission.discodeit.dto.message;

import java.util.List;
import java.util.UUID;

public record MessageResponseDto(
    UUID id,
    UUID authorId,
    UUID channelId,
    String content,
    List<UUID> binaryContents
){}
