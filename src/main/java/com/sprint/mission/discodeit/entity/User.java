package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User {

  private UUID userId;
  private Long createdAt;
  private Long updatedAt;

  private String userName;
  private String nickName;
  private String passWord;

  public User(String userName, String nickName, String passWord) {
    this.userId = UUID.randomUUID();
    this.createdAt = System.currentTimeMillis();
    this.updatedAt = System.currentTimeMillis();

    this.userName = userName;
    this.nickName = nickName;
    this.passWord = passWord;
  }
  public UUID getUserId() {
    return userId;
  }


  public Long getCreatedAt() {
    return createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }
  public void updateNickName(String newNickName){
    this.nickName = newNickName;
    this.updatedAt = System.currentTimeMillis();
  }
  public void updatePassWord(String newPassWord){
    this.passWord = newPassWord;
    this.updatedAt = System.currentTimeMillis();
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", userName='" + userName + '\'' +
        ", nickName='" + nickName + '\'' +
        ", passWord='" + passWord + '\'' +
        '}';
  }
}
