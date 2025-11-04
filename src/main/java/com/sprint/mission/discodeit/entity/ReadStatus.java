package com.sprint.mission.discodeit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "read_statuses",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "read_status_unique_channel_id_key",
                        columnNames = {"channel_id", "user_id"}
                )
        })
@NoArgsConstructor
public class ReadStatus extends BaseUpdatableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @Column(nullable = false)
    private Instant lastReadAt;

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
