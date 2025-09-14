package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.time.Instant;
import java.util.UUID;

public record BinaryContentResponse(
        UUID id,
        Instant createdAt
        // TODO: 바이너리 메타데이터 필드를 추가해야 합니다. (e.g., filename, url, size)
) {
    public static BinaryContentResponse of(BinaryContent binaryContent) {
        return new BinaryContentResponse(
                binaryContent.getId(),
                binaryContent.getCreatedAt()
        );
    }
}
