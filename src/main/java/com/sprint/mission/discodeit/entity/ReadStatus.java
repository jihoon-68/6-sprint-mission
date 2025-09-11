package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus {
    private UUID id;
    private Instant recentRead;

    private UUID userId;
    private UUID channelId;

    public ReadStatus(UUID userId,  UUID channelId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        this.recentRead = null;
    }

    public void read(){
        this.recentRead = Instant.now();
    }

    public boolean hasRead(Message message){
        if(recentRead == null){
            return false;
        }
        return message.getCreatedAt().isBefore(recentRead) ||
                message.getCreatedAt().equals(recentRead);
    }
}
