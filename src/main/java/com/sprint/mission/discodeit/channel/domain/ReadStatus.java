package com.sprint.mission.discodeit.channel.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record ReadStatus(UUID userId, Instant lastReadAt) implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReadStatus that = (ReadStatus) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
