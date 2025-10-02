package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.Duration;
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
public class UserStatus implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private UUID id;
  private Instant createdAt;
  private Instant updatedAt;
  private UUID userId;
  private Instant lastActiveAt;

  private UserStatus(UUID userId, Instant lastActiveAt) {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
    //
    this.userId = userId;
    this.lastActiveAt = lastActiveAt;
  }

  public static UserStatus fromUser(UUID userId, Instant lastActiveAt) {
    return new UserStatus(userId, lastActiveAt);
  }

  public boolean isOnline() {
    Instant instantFiveMinutesAgo = Instant.now().minus(Duration.ofMinutes(5));

    return lastActiveAt.isAfter(instantFiveMinutesAgo);
  }

  public void update(Instant lastActiveAt) {
    boolean anyValueUpdated = false;
    if (lastActiveAt != null && !lastActiveAt.equals(this.lastActiveAt)) {
      this.lastActiveAt = lastActiveAt;
      anyValueUpdated = true;
    }

    if (anyValueUpdated) {
      this.updatedAt = Instant.now();
    }
  }
}
