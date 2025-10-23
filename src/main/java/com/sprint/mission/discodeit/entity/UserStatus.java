package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatus extends BaseUpdatableEntity {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Instant lastActiveAt; // NULL 허용, 수정 가능.

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public boolean isOnline() {
        Instant fiveMinuteAgo = Instant.now().minus(5, ChronoUnit.MINUTES);
        return lastActiveAt.isAfter(fiveMinuteAgo); // 5분전 시각이 마지막 접속시간보다 뒤이면 false 반환
    }
}