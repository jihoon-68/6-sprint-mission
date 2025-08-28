package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

public class Channel implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private final String id;
    private final Long createdAt;
    private final User creator;
    private final List<User> participants = new ArrayList<>(); // 채널에 있는 유저들
    private final List<Message> messages = new ArrayList<>(); // 채널에 올라온 메시지들

    private Long updatedAt;
    private String name;

    public Channel(String name, User creator) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.name = name;
        this.creator = creator;
        this.participants.add(creator);
    }

    // Getter
    public String getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public User getCreator() {
        return creator;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    // Setter
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
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
