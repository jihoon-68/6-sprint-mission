package com.sprint.mission.discodeit.dto.readStatus.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ReadStatusDto {
    private UUID id;
    private UUID userId;
    private boolean read;
    private UUID channelId;
}
