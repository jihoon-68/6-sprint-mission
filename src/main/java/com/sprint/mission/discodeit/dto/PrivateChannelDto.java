package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public final class PrivateChannelDto {

    private PrivateChannelDto() {
    }

    public record Request(Set<UUID> joinedUserIds) {
    }

    public record Response(UUID id, Instant createdAt, Set<UUID> joinedUserIds) {
    }
}
