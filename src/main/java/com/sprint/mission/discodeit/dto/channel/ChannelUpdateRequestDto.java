package com.sprint.mission.discodeit.dto.channel;

public record ChannelUpdateRequestDto (
        String name, // null 허용
        String description // null 허용
) {
}
