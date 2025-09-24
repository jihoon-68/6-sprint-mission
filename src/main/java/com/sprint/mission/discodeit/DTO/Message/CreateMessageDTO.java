package com.sprint.mission.discodeit.DTO.Message;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Message 생성 정보")
public record CreateMessageDTO(
        UUID channelId,
        UUID authorId,
        String content
) {}
