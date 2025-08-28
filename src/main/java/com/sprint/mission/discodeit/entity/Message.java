package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    private final String id;
    private final Long createdAt;
    private final User user;
    private final Channel channel;

    private Long updatedAt;
    private String content; // 내용

    public Message(User user, Channel channel, String content){
        this.id = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.content = content;
        this.user = user;
        this.channel = channel;
    }

    // Getter
    public String getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Channel getChannel() {
        return channel;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getContent() {
        return content;
    }

    // Setter
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setContent(String content) { // 메시지 내용 수정
        this.content = content;
    }

    // toString()
    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", user=" + user.toString() +
                ", channel=" + channel.toString() +
                ", updatedAt=" + updatedAt +
                ", content='" + content + '\'' +
                '}';
    }
}
