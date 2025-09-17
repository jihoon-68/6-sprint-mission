package com.sprint.mission.discodeit.dto.channel;

import java.util.UUID;

public record PublicChannelCreateRequestDto (
        UUID userId,
        String name,
        String description // null 허용
){
}
