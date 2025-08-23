package com.sprint.mission.discodeit.entity;

/**
 * 객체 클래스 User
 * username: String
 * email: String
 * status: UserStatus(ONLINE, OFFLINE, AWAY, DO_NOT_DISTURB)
 */
public class User extends Common {
    private String username;
    private String email;
    private String password;
    private UserStatus status;

    // Constructor
    public User(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = UserStatus.ONLINE;
    }

    // Getter
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserStatus getStatus() { return status; }

    // Setter
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setStatus(UserStatus status) { this.status = status; }

    // UserStatus Enum
    public enum UserStatus {
        ONLINE,
        OFFLINE,
        AWAY,
        DO_NOT_DISTURB
    }

    // Update
    public void update(String username, String email, String password) {
        this.username = username != null ? username : this.username;
        this.email = email != null ? email : this.email;
        this.password = password != null ? password : this.password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                ", username=" + username +
                ", email=" + email +
                ", password=" + password +
                ", status=" + status +
                '}';
    }
}
