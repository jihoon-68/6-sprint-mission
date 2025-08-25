package com.sprint.mission.discodeit.entity;

public class User extends Common{
    private String userName;

    public User(String userName) {
        super();
        if(userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("유저 이름은 비어 있을 수 없습니다.");
        }
        this.userName = userName;
    }

    public String getUserName() {return userName;}
    public void setUserName(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("유저 이름은 비어 있을 수 없습니다.");
        }
        this.userName = userName;
    }

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
