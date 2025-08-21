package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;

  private UUID channelId;
  private Long createdAt;
  private Long updatedAt;

  private String channelName;

  public Channel(String channelName){

    this.channelId = UUID.randomUUID();
    this.createdAt = System.currentTimeMillis();
    this.updatedAt = System.currentTimeMillis();

    this.channelName = channelName;
  }
  public UUID getChannelId() {
    return channelId;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public Long getUpdatedAt() {
    return updatedAt;
  }

  public String getChannelName() {
    return channelName;
  }

  public void updateChannelName(String newChannelName) {
    this.channelName = newChannelName;
    this.updatedAt = System.currentTimeMillis();
  }

  @Override
  public String toString() {
    return "Channel{" +
        "channelId=" + channelId +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", channelName='" + channelName + '\'' +
        '}';
  }
}
