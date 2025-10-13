package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Instant;
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
public class Channel extends BaseUpdatableEntity {

  @Enumerated(EnumType.STRING)
  private ChannelType type;
  private String name;
  private String description;

  // 정적 팩토리 메서드
  public static Channel createPublic(String name, String description) {
    return Channel
        .builder()
        .type(ChannelType.PUBLIC)
        .name(name)
        .description(description)
        .build();
  }

  public static Channel createPrivate() {
    return Channel
        .builder()
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
