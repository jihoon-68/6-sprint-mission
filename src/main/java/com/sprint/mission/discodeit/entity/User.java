package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User {
    private final UUID userId;
    private final Long createdAt;
    private Long updatedAt;
    private String userName;

    public User(String userName) {
        this.userId = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = null;
        this.userName = userName;
    }

    //getter
    public UUID getuserId() {
        return this.userId;
    }

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public Long getUpdatedAt() {
        return this.updatedAt;
    }

    public String getUserName() {
        return this.userName;
    }

    //update Method
    public void updateUser(String userName) {
        this.updatedAt = System.currentTimeMillis();
        this.userName = userName;
    }





}
