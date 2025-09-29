package com.sprint.mission.discodeit.dto.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentDTO(
        UUID userId,
        UUID channelID,
        String filePath
) {}
