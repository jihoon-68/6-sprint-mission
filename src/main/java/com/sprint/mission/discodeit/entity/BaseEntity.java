package com.sprint.mission.discodeit.entity;

import java.time.Instant;

public abstract class BaseEntity {
    public Instant setTime() {
        return Instant.now();
    }
}
