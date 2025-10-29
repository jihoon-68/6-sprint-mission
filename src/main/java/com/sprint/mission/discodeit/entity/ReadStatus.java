package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;

import java.time.Instant;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "read_statuses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadStatus extends BaseUpdatableEntity<ReadStatusId> {

    @EmbeddedId
    private ReadStatusId id;

    @Override
    public ReadStatusId getId() {
        return id;
    }

    @MapsId("user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
    private User pkUser;

    @MapsId("channel")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", columnDefinition = "BINARY(16)")
    private Channel pkChannel;

    @Column(nullable = false)
    private Instant lastReadAt;

    public ReadStatus(User user, Channel channel, Instant lastReadAt) {
        this.id = new ReadStatusId(user.getId(), channel.getId());
        this.pkUser = user;
        this.pkChannel = channel;
        this.lastReadAt = lastReadAt;
    }

    public void update(Instant newLastReadAt) {
        if (newLastReadAt != null) {
            this.lastReadAt = newLastReadAt;
        }
    }
}
