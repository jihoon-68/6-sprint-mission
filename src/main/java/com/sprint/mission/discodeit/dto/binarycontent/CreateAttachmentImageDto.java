package com.sprint.mission.discodeit.dto.binarycontent;

import java.util.UUID;

public record CreateAttachmentImageDto (
        UUID messageId,
        String imagePath
){}
