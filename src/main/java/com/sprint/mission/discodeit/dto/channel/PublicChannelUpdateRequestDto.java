package com.sprint.mission.discodeit.dto.channel;

public record PublicChannelUpdateRequestDto (
        String newName, // null 허용
        String newDescription // null 허용
) {
}
