package com.sprint.mission.discodeit.dto.channel;

import java.util.UUID;

public record ChannelUpdateRequestDto (
        UUID id,
        String name, // null 허용
        String description // null 허용
) {
}
