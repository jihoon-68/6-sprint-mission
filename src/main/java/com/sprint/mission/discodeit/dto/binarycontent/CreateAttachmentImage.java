package com.sprint.mission.discodeit.dto.binarycontent;

import java.util.UUID;

public record CreateAttachmentImage(
        UUID messageId,
        byte[] bytes
){}
