package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Channel implements Serializable {

  private static final long serialVersionUID = 1L;
  private UUID id;
  private Instant createdAt;
  private Instant updatedAt;
  //
  private ChannelType type;
  private String name;
  private String description;

  //공개 채널 생성자
  private Channel(String name, String description) {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
    //
    this.type = ChannelType.PUBLIC;
    this.name = name;
    this.description = description;
  }

  //비공개 채널 생성자
  private Channel() {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
    this.type = ChannelType.PRIVATE;
  }

  // 정적 팩토리 메서드
  public static Channel createPublic(String name, String description) {
    return new Channel(name, description);
  }

  public static Channel createPrivate() {
    return new Channel();
  }


  public void update(String newName, String newDescription) {
    boolean anyValueUpdated = false;
    if (!newName.equals(this.name)) {
      this.name = newName;
      anyValueUpdated = true;
    }
    if (!newDescription.equals(this.description)) {
      this.description = newDescription;
      anyValueUpdated = true;
    }

    if (anyValueUpdated) {
      this.updatedAt = Instant.now();
    }
  }
}
