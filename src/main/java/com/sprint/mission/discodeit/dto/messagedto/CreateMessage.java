package com.sprint.mission.discodeit.dto.messagedto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record CreateMessage(
        @NotBlank String content,
        @NotBlank UUID channelId,
        @NotBlank UUID authorId,
        List<UUID> attachmentIds
) {
    public CreateMessage(String content, UUID channelId, UUID authorId) {
        this(content,channelId,authorId,null);
    }
}
