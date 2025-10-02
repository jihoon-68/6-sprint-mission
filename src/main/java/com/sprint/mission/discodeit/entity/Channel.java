package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Channel implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private UUID id;
  private Instant createdAt;
  private Instant updatedAt;
  //
  @Enumerated(EnumType.STRING)
  private ChannelType type;
  private String name;
  private String description;

  // 정적 팩토리 메서드
  public static Channel createPublic(String name, String description) {
    return Channel
        .builder()
        .id(UUID.randomUUID())
        .createdAt(Instant.now())
        .type(ChannelType.PUBLIC)
        .name(name)
        .description(description)
        .build();
  }

  public static Channel createPrivate() {
    return Channel
        .builder()
        .id(UUID.randomUUID())
        .createdAt(Instant.now())
        .type(ChannelType.PRIVATE)
        .build();
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
