package com.sprint.mission.discodeit.dto.request;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.Base64;

public record BinaryContentCreateRequest(
        String fileName,
        String contentType,
        byte[] bytes
) {
}
