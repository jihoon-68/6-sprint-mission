package com.sprint.mission.discodeit.user.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public record UserStatus(Instant lastActivatedAt) implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private static final Duration ONLINE_TIMEOUT_IN_MINUTES = Duration.ofMinutes(5);

    public static UserStatus of() {
        return new UserStatus(Instant.MIN);
    }

    public boolean isOnline(Instant now) {
        Duration elapsed = Duration.between(lastActivatedAt, now);
        return elapsed.compareTo(ONLINE_TIMEOUT_IN_MINUTES) < 0;
    }
}
