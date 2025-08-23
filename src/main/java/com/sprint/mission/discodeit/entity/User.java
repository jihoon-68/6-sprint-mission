package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {
    private UUID id;
    private Long updateAt;
    private Long createAt;
    private String email;
    private String username;
    private String password;
    private static final long serializableId = 1L;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID();
        this.createAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateUsername(String username) {
        this.username = username;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateEmail(String email) {
        this.email = email;
        this.updateAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nupdateAt: " + updateAt +
                "\ncreateAt: " + createAt +
                "\nemail: '" + email + '\'' +
                "\nusername: '" + username + '\'' +
                "\npassword: '" + password + '\'';
    }
}
