package com.sprint.mission.discodeit.DTO.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentDTO(
        UUID userId,
        UUID channelID,
        byte[] content
) {}
