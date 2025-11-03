package com.sprint.mission.discodeit.dto.message;


import java.util.UUID;

public record MessageCreateRequestDto(
        UUID userId,
        UUID channelId,
        String content
        // List<BinaryContent> binaryContents // null 허용
) {}
