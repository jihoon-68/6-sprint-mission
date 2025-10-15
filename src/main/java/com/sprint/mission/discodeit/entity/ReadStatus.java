package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "read_statuses")
@IdClass(ReadStatus.ReadStatusId.class) //복합키클레스 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadStatus extends BaseUpdatableEntity {

  @Id  //복합키 일부
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
  private User user;

  @Id  //복합키 일부
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id", columnDefinition = "BINARY(16)")
  private Channel channel;

  @Column(nullable = false)
  private Instant lastReadAt;

  public ReadStatus(User user, Channel channel, Instant lastReadAt) {
    this.user = user;
    this.channel = channel;
    this.lastReadAt = lastReadAt;
  }

  public void update(Instant newLastReadAt) {
    if (newLastReadAt != null) {
      this.lastReadAt = newLastReadAt;
    }
  }

  // 복합키 클래스 정의
  @Getter
  @EqualsAndHashCode
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @AllArgsConstructor
  public static class ReadStatusId implements Serializable {
    private UUID user;
    private UUID channel;
  }
}
