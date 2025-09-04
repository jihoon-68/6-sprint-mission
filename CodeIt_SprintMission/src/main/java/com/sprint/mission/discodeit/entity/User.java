package com.sprint.mission.discodeit.entity;

import java.io.Serializable;

public class User extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String userEmail;

    public User(String userName, String userEmail){
        super();
        this.userName = userName;
        this.userEmail = userEmail;

    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        setUpdateAt(System.currentTimeMillis());
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        setUpdateAt(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + getId() +
                ", name='" + userName + '\'' +
                ", email='" + userEmail + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdateAt() +
                '}';
    }
}
