package com.sprint.mission.discodeit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    private UUID id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    BaseEntity() {
        id = UUID.randomUUID();
    }
}
