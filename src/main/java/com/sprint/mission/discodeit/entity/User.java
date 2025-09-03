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
    private List<UUID> friendIds;
    private List<UUID> sentFriendRequests;
    private List<UUID> receivedFriendRequests;
    private static final long serializableId = 1L;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID();
        this.createAt = System.currentTimeMillis();
        this.friendIds = new ArrayList<>();
        this.sentFriendRequests = new ArrayList<>();
        this.receivedFriendRequests = new ArrayList<>();
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

    public List<UUID> getFriendIds() {
        return friendIds;
    }

    public List<UUID> getSentFriendRequests() {
        return sentFriendRequests;
    }

    public List<UUID> getReceivedFriendRequests() {
        return receivedFriendRequests;
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

    public void updateReceivedFriendRequests(List<UUID> receivedFriendRequests) {
        this.receivedFriendRequests = receivedFriendRequests;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateSentFriendRequests(List<UUID> sentFriendRequests) {
        this.sentFriendRequests = sentFriendRequests;
        this.updateAt = System.currentTimeMillis();
    }

    public void updateFriendIds(List<UUID> friendIds) {
        this.friendIds = friendIds;
        this.updateAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nupdateAt: " + updateAt +
                "\ncreateAt: " + createAt +
                "\nemail: '" + email + '\'' +
                "\nusername: '" + username + '\'' +
                "\npassword: '" + password + '\'' +
                "\nfriendIds: " + friendIds;
    }
}
