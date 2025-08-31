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
        this.setUpdatedAt(System.currentTimeMillis());
    }
}
