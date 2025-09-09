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

    public ReadStatus(User user, Channel channel) {
        this.id = UUID.randomUUID();
        this.userId = user.getId();
        this.channelId = channel.getId();
        this.lastReadAt = Instant.EPOCH;
    }

    public boolean lastReadMessageTime(Instant latestMessageTime){
        return lastReadAt.isBefore(latestMessageTime);
    }

    public void updateLastReadAt(Instant time) {
        this.lastReadAt = time;
    }

}
