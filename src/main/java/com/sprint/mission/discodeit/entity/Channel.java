package com.sprint.mission.discodeit.entity;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Channel extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    private static final String PUBLIC_CHANNEL_CANNOT_HAVE_JOINED_USERS = "Public channel cannot have joined users";
    private static final String PRIVATE_CHANNEL_REQUIRES_AT_LEAST_2_USERS = "Private channel requires at least 2 users";
    private static final String CHANNEL_NAME_CANNOT_BE_BLANK = "Channel name cannot be blank.";
    private static final String PRIVATE_CHANNEL_IS_READ_ONLY = "Private channel is read-only";

    private final ChannelType channelType;
    private final String channelName;
    private final String description;
    private final Set<UUID> joinedUserIds;

    public enum ChannelType {PUBLIC, PRIVATE,}

    public Channel(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            ChannelType channelType,
            String channelName,
            String description,
            Set<UUID> joinedUserIds
    ) {
        super(id, createdAt, updatedAt);
        if (channelType == ChannelType.PUBLIC && !joinedUserIds.isEmpty()) {
            throw new IllegalArgumentException(PUBLIC_CHANNEL_CANNOT_HAVE_JOINED_USERS);
        }
        if (channelType == ChannelType.PRIVATE && joinedUserIds.size() < 2) {
            throw new IllegalArgumentException(PRIVATE_CHANNEL_REQUIRES_AT_LEAST_2_USERS);
        }
        if (channelName.isBlank()) {
            throw new IllegalArgumentException(CHANNEL_NAME_CANNOT_BE_BLANK);
        }
        this.channelType = channelType;
        this.channelName = channelName.trim();
        this.description = description.trim();
        this.joinedUserIds = Set.copyOf(joinedUserIds);
    }

    public static Channel ofPublic(String channelName, String description) {
        return of(ChannelType.PUBLIC, channelName, description, Collections.emptySet());
    }

    public static Channel ofPrivate(Set<UUID> joinedUserIds) {
        return of(ChannelType.PRIVATE, "PRIVATE", "", joinedUserIds);
    }

    private static Channel of(
            ChannelType channelType,
            String channelName,
            String description,
            Set<UUID> joinedUserIds
    ) {
        return new Channel(
                null,
                null,
                null,
                channelType,
                channelName,
                description,
                joinedUserIds
        );
    }

    public Channel update(String channelName, String description) {
        if (channelType == ChannelType.PRIVATE) {
            throw new UnsupportedOperationException(PRIVATE_CHANNEL_IS_READ_ONLY);
        }
        return new Channel(
                this.getId(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.channelType,
                channelName,
                description,
                this.joinedUserIds
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
        return joinedUserIds;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelType=" + channelType +
                ", channelName='" + channelName + '\'' +
                ", description='" + description + '\'' +
                ", joinedUserIds=" + joinedUserIds +
                "} " + super.toString();
    }
}
