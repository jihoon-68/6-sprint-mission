package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
  private UUID messageId;
  private Long createdAt;
  private Long updatedAt;
  String text;

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
        '}';
  }
}
