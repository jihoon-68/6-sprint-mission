package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.dto.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.enumtype.UserStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;


@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "user_statuses")
public class UserStatus extends BaseUpdatableEntity{

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Column(nullable = false, unique = true)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private Instant lastAccessAt;

    public UserStatus(User user) {
        super();
        this.user = user;
        this.lastAccessAt = Instant.now();
    }

    public void update(Instant lastAccessAt) {
        boolean anyValueUpdated = false;

        if (lastAccessAt != null && !lastAccessAt.equals(this.lastAccessAt)){
            this.lastAccessAt = lastAccessAt;
            anyValueUpdated = true;
        }
        if(anyValueUpdated){
            this.updatedAtNow();
        }
    }

    //접속 학인
    public boolean isConnecting(Instant newLastAccessAt) {
        long duration = newLastAccessAt.toEpochMilli() - this.lastAccessAt.toEpochMilli();
        if (duration <= 300000) {
            this.lastAccessAt = Instant.now();
            return true;
        }
        return false;
    }
}
