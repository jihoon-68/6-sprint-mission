package com.sprint.mission.discodeit.dto.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentDTO(
        UUID userId,
        UUID channelID,
<<<<<<< HEAD
        String filePath
=======
        byte[] content
>>>>>>> 7c7532b (박지훈 sprint3 (#2))
) {}
