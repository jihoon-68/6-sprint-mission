package com.sprint.mission.discodeit.DTO.Message;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;

public record FindMessageDTO(
        Message message,
        List<String> files
) {
}
