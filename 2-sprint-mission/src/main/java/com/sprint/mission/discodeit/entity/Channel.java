package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Channel(
        UUID id,
        Long createdAt,
        Long updatedAt,
        ChannelType channelType,
        String channelName,
        String description
) implements Serializable {

    public enum ChannelType {
        PUBLIC,
        PRIVATE
    }

    @Serial
    private static final long serialVersionUID = 1L;

    public Channel(
            UUID id,
            Long createdAt,
            Long updatedAt,
            ChannelType channelType,
            String channelName,
            String description
    ) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: 'null'");
        }
        if (channelName == null || channelName.isBlank()) {
            throw new IllegalArgumentException("Invalid channelName: '%s'".formatted(channelName));
        }
        this.id = id;
        this.createdAt = Objects.requireNonNullElseGet(createdAt, () -> Instant.now().toEpochMilli());
        this.updatedAt = Objects.requireNonNullElse(updatedAt, createdAt);
        this.channelType = Objects.requireNonNullElse(channelType, ChannelType.PUBLIC);
        this.channelName = channelName.trim();
        this.description = Objects.requireNonNullElse(description, "").trim();
    }

    public static Channel of(ChannelType channelType, String channelName, String description) {
        long now = Instant.now().toEpochMilli();
        return new Channel(
                UUID.randomUUID(),
                now,
                now,
                channelType,
                channelName,
                description
        );
    }

    public Channel setPublicChannel() {
        if (channelType == ChannelType.PUBLIC) {
            throw new IllegalStateException("Illegal transition: channel already PUBLIC");
        }
        return new Channel(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                ChannelType.PUBLIC,
                channelName,
                description
        );
    }

    public Channel updateChannelName(String newChannelName) {
        return new Channel(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                channelType,
                newChannelName,
                description
        );
    }

    public Channel updateDescription(String newDescription) {
        return new Channel(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                channelType,
                channelName,
                newDescription
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Channel channel)) return false;
        return Objects.equals(id, channel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
