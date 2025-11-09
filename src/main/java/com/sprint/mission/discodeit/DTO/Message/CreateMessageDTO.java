package com.sprint.mission.discodeit.DTO.Message;

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Message 생성 정보")
public record CreateMessageDTO(
        UUID channelId,
        UUID authorId,
        String content
=======
import java.util.List;
import java.util.UUID;

public record CreateMessageDTO(
        UUID channelId,
        UUID userId,
        String content,
        List<byte[]> attachments
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
) {}
