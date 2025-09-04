package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus {
    private static final long serialVersionUID = 1L;
    private UUID Id;
    private UUID userId;
    private UUID channelId;
    private Instant createdAt;
    private Instant updatedAt;

    public ReadStatus(UUID userId, UUID channelId) {
        this.userId = userId;
        this.channelId = channelId;
        this.Id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }

    //ReadStatus 는 업데이트 할게 없어 보이는데, 요구사항에 의해 userId, channelId를 업데이트 함.
    //원래대로 라면 읽음 여부만 false -> true로 변경하게 하는 메소드만 있으면 될것으로 보임
    public void update(UUID newUserId, UUID newChannelId) {
        boolean anyValueUpdated = false;
        if (newUserId != null && !newUserId.equals(this.userId)) {
            this.userId = newUserId;
            anyValueUpdated = true;
        }

        if (newChannelId != null && !newChannelId.equals(this.channelId)) {
            this.channelId = newChannelId;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }
}
