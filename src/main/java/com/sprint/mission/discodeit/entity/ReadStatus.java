package com.sprint.mission.discodeit.entity;


import com.sprint.mission.discodeit.DTO.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.Enum.ReadType;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant created;
    private Instant updated;

    private UUID channelId;
    private UUID userId;
    private ReadType type;

    public ReadStatus(UUID channelId, UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;

        this.type = ReadType.UNREAD;
        this.created = Instant.now();
    }

    public void update(UpdateReadStatusDTO updateReadStatusDTO) {
        boolean anyValueUpdated = false;
        if (updateReadStatusDTO.channelId() != null && !updateReadStatusDTO.channelId().equals(this.channelId)) {
            this.channelId = updateReadStatusDTO.channelId();
            anyValueUpdated = true;
        }
        if (updateReadStatusDTO.userId() != null && !updateReadStatusDTO.userId().equals(this.userId)) {
            this.userId = updateReadStatusDTO.userId();
            anyValueUpdated = true;
        }
        if (updateReadStatusDTO.type() != null && !updateReadStatusDTO.type().equals(this.type)) {
            this.type = updateReadStatusDTO.type();
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            this.updated = Instant.now();
        }
    }
}
