package com.sprint.mission.discodeit.DTO.Message;

import java.util.List;
import java.util.UUID;

public record UpdateMessageDTO(
        UUID id,
        String Content,
        List<UUID> attachmentIds
) {}
