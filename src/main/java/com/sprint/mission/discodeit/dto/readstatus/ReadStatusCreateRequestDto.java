package com.sprint.mission.discodeit.dto.readstatus;

import com.sprint.mission.discodeit.entity.BinaryContentType;

import java.util.UUID;

public record ReadStatusCreateRequestDto (
        UUID userId,
        UUID channelId
){
}
