package com.sprint.mission.discodeit.DTO.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentRequest(
        UUID profileId,
        UUID messageId,
        String content
) {
}
