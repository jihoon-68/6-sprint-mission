package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Objects;

public class Channel extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public Channel(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void updateChannel(String newName, String newDescription) {
        boolean nameChanged = (newName != null) && !Objects.equals(this.name, newName);
        boolean descriptionChanged = (newDescription != null) && !Objects.equals(this.description, newDescription);

        if (!nameChanged && !descriptionChanged) {
            System.out.println("Name and Description are not change");
            return;
        }

        if (nameChanged) this.name = newName;
        if (descriptionChanged) this.description = newDescription;
        this.setUpdatedAt();
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelName='" + name + '\'' +
                ", channelDescription='" + description + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdateAt() +
                '}';
    }
}
