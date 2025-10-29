package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_statuses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus extends BaseUpdatableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Override
    public UUID getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "last_active_at", nullable = false)
    private Instant lastActiveAt;

    public UserStatus(User user, Instant lastActiveAt) {
        this.user = user;
        this.lastActiveAt = lastActiveAt;
    }

    public void update(Instant lastActiveAt, Boolean online) {
        if (lastActiveAt != null) {
            this.lastActiveAt = lastActiveAt;
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
