package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Channel extends BaseEntity {
    private ChannelType type;
    private String name;
    private String description;

    public Channel(ChannelType type, String name, String description) {
        super();
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public ChannelType getType() { return type; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void update(ChannelType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
        super.touch();
    }
}