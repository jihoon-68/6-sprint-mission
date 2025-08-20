package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
  private UUID messageId;
  private Long createdAt;
  private Long updatedAt;
  String text;

  /// 서비스간 의존성 주입
  private User user; //메시지 생성 사용자 정보
  private Channel channel; // 메시지 속하는 채널 정보

  public Message(String text) {
    this.messageId = UUID.randomUUID();
    this.createdAt = System.currentTimeMillis();
    this.updatedAt = System.currentTimeMillis();

    this.text = text;
  }

  public UUID getMessageId() {
    return messageId;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void updateText(String newText) {
    this.text = newText;
    this.updatedAt = System.currentTimeMillis();
  }

  @Override
  public String toString() {
    return "Message{" +
        "messageId=" + messageId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", text='" + text + '\'' +
        ", user=" + user +
        ", channel=" + channel +
        '}';
  }

  public void setUser(User user) {
    this.user = user;
  }
  public void setChannel(Channel channel) {
    this.channel = channel;
  }
}
