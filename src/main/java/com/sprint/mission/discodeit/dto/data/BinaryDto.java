package com.sprint.mission.discodeit.dto.data;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;

public record BinaryDto(
    UUID id,
    String fileName,
    long fileSize,
    String contentType
) {

    public static BinaryDto from(BinaryContent binaryContent) {
        return new BinaryDto(
            binaryContent.getId(),
            binaryContent.getFileName(),
            binaryContent.getSize(),
            binaryContent.getContentType()
        );
    }
}
