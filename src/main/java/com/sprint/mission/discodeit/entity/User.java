package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Getter
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Instant createdAt;
    private final List<UUID> joinedChannels = new ArrayList<>(); // 속해있는 채널
    private final List<UUID> createdMessages = new ArrayList<>(); // 작성한 메시지

    private Instant updatedAt;
    private String email;
    private String username;
    private transient String password;

    public User(String email, String username, String password, UUID profileImageId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Setter

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = Instant.now();
    }

    public void setUsername(String username) {
        this.username = username;
        this.updatedAt = Instant.now();
    }

    public void setPassword(String password) {
        this.password = password;
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", joinedChannels=" + joinedChannels +
                ", createdMessages=" + createdMessages +
                ", updatedAt=" + updatedAt +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
