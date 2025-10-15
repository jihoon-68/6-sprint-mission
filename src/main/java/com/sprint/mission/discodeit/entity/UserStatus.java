package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus extends BaseUpdatableEntity {

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private Instant lastActiveAt;

  @Column(nullable = false)
  @Setter
  private Boolean status;

  public UserStatus(User user, Instant lastActiveAt , Boolean status) {
    this.user = user;
    this.lastActiveAt = lastActiveAt;
    this.status = status;
  }

  public void update(Instant lastActiveAt , Boolean status) {
    if (lastActiveAt != null) {
      this.lastActiveAt = lastActiveAt;
    }
    if (status != null) {
      this.status = status;
    }
  }

  public void update(Instant lastActiveAt) {
    if (lastActiveAt != null) {
      this.lastActiveAt = lastActiveAt;
    }
  }

  @Transient
  public Boolean isOnline() {
    Instant instantFiveMinutesAgo = Instant.now().minus(Duration.ofMinutes(5));
    return lastActiveAt.isAfter(instantFiveMinutesAgo);
  }
}
