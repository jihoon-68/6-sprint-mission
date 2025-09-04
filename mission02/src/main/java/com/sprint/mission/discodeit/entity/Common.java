package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Common {
    private UUID uuid;
    private Instant createdAt;
    private Instant updatedAt;

    public Common(){
        this.uuid = UUID.randomUUID();
        this.createdAt = Instant.now();
    }
}
