package com.sprint.mission.discodeit.DTO.Message;

import java.util.UUID;

public record CreateMessageDTO(
        UUID channelId,
        UUID userId,
        String content
) {}
