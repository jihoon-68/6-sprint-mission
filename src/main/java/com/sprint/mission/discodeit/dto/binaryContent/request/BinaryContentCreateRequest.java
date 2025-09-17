package com.sprint.mission.discodeit.dto.binaryContent.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BinaryContentCreateRequest {
    private UUID userId;
    private UUID messageId;
    private String path;
}
