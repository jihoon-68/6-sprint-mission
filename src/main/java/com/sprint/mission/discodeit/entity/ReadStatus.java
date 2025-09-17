package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userId;
    private UUID channelId;
    private Instant lastReadAt;

    public ReadStatus(UUID userId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        this.lastReadAt = Instant.EPOCH;    //  초기화
    }

    public boolean hasUnreadMessage(Instant latestMessageTime){
        return lastReadAt.isBefore(latestMessageTime);  // lastReadAt == lastestMessageTime -> false, 읽은 메시지
    }

    public void updateLastReadAt(Instant time) {
        this.lastReadAt = time;
    }

}
