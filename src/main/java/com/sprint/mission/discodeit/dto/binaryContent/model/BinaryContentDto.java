package com.sprint.mission.discodeit.dto.binaryContent.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BinaryContentDto {
    private UUID id;
    private String path;
}
