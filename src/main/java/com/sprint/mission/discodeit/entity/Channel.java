package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Getter
public class Channel implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private final UUID id;
    private final UUID userId; // 채널 생성인
    private final Instant createdAt;
    private final List<UUID> participants = new ArrayList<>(); // 채널에 있는 유저들
    private final List<UUID> messages = new ArrayList<>(); // 채널에 올라온 메시지들

    private ChannelType channelType;
    private Instant updatedAt;
    private String name;
    private String description;

    public Channel(UUID userId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = userId;
        this.participants.add(userId);
    }

    // Setter
    public void setName(String name) {
        this.name = name;
        this.updatedAt = Instant.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    // toString()
    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                // ", participants=" + participants +
                // ", messages=" + messages +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                '}';
    }
}
