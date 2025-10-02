package com.sprint.mission.discodeit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;


@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "read_statuses")
public class ReadStatus extends BaseUpdatableEntity{

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(nullable = false)
    private Instant lastReadAt ;

    public ReadStatus(Channel channel, User user) {
        this.user = user;
        this.channel = channel;
        this.lastReadAt = Instant.now();
    }

    public void update(Instant lastReadAt) {
        boolean anyValueUpdated = false;
        if (lastReadAt != null && !lastReadAt.equals(this.lastReadAt)) {
            this.lastReadAt = lastReadAt;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAtNow();
        }
    }

}
