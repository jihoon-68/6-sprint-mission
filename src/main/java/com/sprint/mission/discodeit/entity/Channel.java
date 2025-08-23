package com.sprint.mission.discodeit.entity;

/**
 * 객체 클래스 Channel
 * title: String
 * description: String
 * type: ChannelType(TEXT, VOICE)
 */
public class Channel extends Common{
    private String title;
    private String description;
    private ChannelType type;

    // Constructor
    public Channel(String title, String description, ChannelType type) {
        super();
        this.title = title;
        this.description = description;
        this.type = type;
    }

    // Getter
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ChannelType getType() { return type; }

    // Setter
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setType(ChannelType type) { this.type = type; }

    // enum
    public enum ChannelType {
        TEXT,
        VOICE
    }

    // Update
    public void update(String title, String description) {
        this.title = title != null ? title : this.title;
        this.description = description != null ? description : this.description;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + getId() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                ", title=" + title +
                ", description=" + description +
                ", type=" + type +
                '}';
    }
}