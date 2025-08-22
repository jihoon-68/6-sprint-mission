package com.sprint.mission.discodeit.entity;

public class User extends Common{
    private String userName;

    public User(String userName) {
        super();
        this.userName = userName;
    }

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", uuid=" + getUuid() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
