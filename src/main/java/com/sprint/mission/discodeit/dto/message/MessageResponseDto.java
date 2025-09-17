package com.sprint.mission.discodeit.dto.message;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.UUID;

public record MessageResponseDto(
    UUID id,
    UUID userId,
    UUID channelId,
    String content,
    List<BinaryContent> binaryContents
){}
