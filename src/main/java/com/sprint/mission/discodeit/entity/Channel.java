package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channels")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseUpdatableEntity {

  @Enumerated(EnumType.STRING)  // Enum 이름 DB에 저장
  @Column(nullable = false)
  private ChannelType type;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 500)
  private String description;


  public Channel(ChannelType type, String name, String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }

  public void update(String newName, String newDescription) {
    if (newName != null) {
      this.name = newName;
    }
    if (newDescription != null) {
      this.description = newDescription;
    }
  }
}
