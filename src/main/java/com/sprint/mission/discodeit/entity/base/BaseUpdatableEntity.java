package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
public class BaseUpdatableEntity extends BaseEntity {

  @LastModifiedDate
  @Column(name="updatedAt", nullable=false)
  private Instant updatedAt;
}
