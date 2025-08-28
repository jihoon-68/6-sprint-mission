package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;
    private final Long createdAt;
    private final List<Channel> joinedChannels = new ArrayList<>(); // 속해있는 채널
    private final List<Message> createdMessages = new ArrayList<>(); // 작성한 메시지

    private Long updatedAt;
    private String email;
    private String userName;
    private transient String password;

    public User(String email, String userName, String password) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    // Getter
    public String getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public List<Channel> getJoinedChannels() {
        return joinedChannels;
    }

    public List<Message> getCreatedMessages() {
        return createdMessages;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = System.currentTimeMillis();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.updatedAt = System.currentTimeMillis();
    }

    public void setPassword(String password) {
        this.password = password;
        this.updatedAt = System.currentTimeMillis();
    }

    // toString()
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                // ", joinedChannels=" + joinedChannels +
                // ", createdMessages=" + createdMessages +
                ", updatedAt=" + updatedAt +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
