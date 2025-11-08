package com.sprint.mission.discodeit.dto.Channel;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PrivateChannelCreateRequest (
        List<UUID> participantIds
) {
}
