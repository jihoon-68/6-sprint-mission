package com.sprint.mission.discodeit.dto.message;

import java.util.UUID;

public record BinaryContentDto(
    UUID id,
    String contentType,
    String filename,
    Long size,
    byte[] bytes
) {

}