package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus {
    private UUID id;
    private UUID userId;
    private UUID channelId;
    private User user;
    private Channel channel;
    private Instant lastReadAt;

    public ReadStatus(UUID userId, UUID channelId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        this.lastReadAt = Instant.EPOCH;    //  초기화
    }

    public boolean lastReadMessageTime(Instant latestMessageTime){
        return lastReadAt.isBefore(latestMessageTime);
    }

    public void updateLastReadAt(Instant time) {
        this.lastReadAt = time;
    }

}
