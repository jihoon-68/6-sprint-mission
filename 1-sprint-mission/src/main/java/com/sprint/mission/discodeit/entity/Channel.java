package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.DTOs.Channel.ChannelUpdate;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Getter
public class Channel extends Common implements Serializable  {
    private static final long serialVersionUID = 1L;
    //
    private final UUID id;
    private final ChannelType type;  // PRIVATE / PUBLIC
    private String name;             // PRIVATE면 규칙상 null/auto-name 가능
    private String description;

    private Channel(UUID id, ChannelType type, String name, String description, Set<UUID> members) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public static Channel newPublic(String name, String description) {
        return new Channel(UUID.randomUUID(), ChannelType.PUBLIC, name, description, null);
    }

    // Private 채널 전용 팩토리
    public static Channel newPrivate(Set<UUID> participants) {
        return new Channel(UUID.randomUUID(), ChannelType.PRIVATE, null, null, participants);
    }

    public void update(ChannelUpdate update) {
        boolean anyValueUpdated = false;
        if (update.name() != null && !update.name().equals(this.name)) {
            this.name = update.name();
            anyValueUpdated = true;
        }
        if (update.description() != null && !update.description().equals(this.description)) {
            this.description = update.description();
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            super.refreshUpdatedAt();
        }
    }
}
