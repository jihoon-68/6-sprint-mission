package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
public abstract class BaseUpdatableEntity<T> extends BaseEntity<T> {

  @LastModifiedDate
  @Column(name="updated_at", nullable=false)
  private Instant updatedAt;
}
