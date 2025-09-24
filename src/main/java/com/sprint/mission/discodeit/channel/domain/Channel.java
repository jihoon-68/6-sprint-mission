package com.sprint.mission.discodeit.channel.domain;

import com.sprint.mission.discodeit.channel.domain.ChannelException.BlankChannelNameException;
import com.sprint.mission.discodeit.channel.domain.ChannelException.InsufficientChannelParticipantsException;
import com.sprint.mission.discodeit.channel.domain.ChannelException.PrivateChannelModificationNotAllowedException;
import com.sprint.mission.discodeit.common.persistence.BaseEntity;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Channel extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    private final ChannelType channelType;
    private final String channelName;
    private final String description;
    private final Set<ReadStatus> readStatuses;

    public enum ChannelType {PUBLIC, PRIVATE,}

    private Channel(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            ChannelType channelType,
            String channelName,
            String description,
            Set<ReadStatus> readStatuses
    ) {
        super(id, createdAt, updatedAt);
        if (channelName.isBlank()) {
            throw new BlankChannelNameException();
        }
        this.channelType = channelType;
        this.channelName = channelName.trim();
        this.description = description.trim();
        this.readStatuses = readStatuses;
    }

    public static Channel ofPublic(String channelName, String description) {
        return of(
                ChannelType.PUBLIC,
                channelName,
                description,
                Collections.emptySet()
        );
    }

    public static Channel ofPrivate(Set<UUID> joinedUserIds) {
        if (joinedUserIds.size() < 2) {
            throw new InsufficientChannelParticipantsException();
        }
        Set<ReadStatus> readStatuses = joinedUserIds.stream()
                .map(joinedUserId -> new ReadStatus(joinedUserId, Instant.MIN))
                .collect(Collectors.toSet());
        return of(
                ChannelType.PRIVATE,
                "PRIVATE",
                "",
                readStatuses
        );
    }

    private static Channel of(
            ChannelType channelType,
            String channelName,
            String description,
            Set<ReadStatus> readStatuses
    ) {
        return new Channel(
                null,
                null,
                null,
                channelType,
                channelName,
                description,
                readStatuses
        );
    }

    public Channel with(String channelName, String description) {
        if (channelType == ChannelType.PRIVATE) {
            throw new PrivateChannelModificationNotAllowedException();
        }
        return new Channel(
                this.getId(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.channelType,
                channelName,
                description,
                this.readStatuses
        );
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getDescription() {
        return description;
    }

    public Set<UUID> getJoinedUserIds() {
        return readStatuses.stream()
                .map(ReadStatus::userId)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelType=" + channelType +
                ", channelName='" + channelName + '\'' +
                ", description='" + description + '\'' +
                ", readStatuses=" + readStatuses +
                "} " + super.toString();
    }
}
