package com.sprint.mission.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ReadStatus extends EntityCommon{

    private UUID userId;
    private UUID channelId;
    private Instant lastReadAt;

    public ReadStatus(UUID userId, UUID channelId) {
        super();
        this.userId = userId;
        this.channelId = null;
        this.lastReadAt = Instant.now();
    }

}
