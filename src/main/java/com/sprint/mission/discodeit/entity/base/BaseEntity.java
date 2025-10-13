package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

  @Id
  protected UUID id;
  @CreatedDate
  protected Instant createdAt;

  protected BaseEntity() {
    this.id = UUID.randomUUID();
  }
}
