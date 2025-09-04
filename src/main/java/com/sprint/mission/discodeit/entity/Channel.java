package com.sprint.mission.discodeit.entity;

/**
 * 객체 클래스 Channel
 * title: String
 * description: String
 * type: ChannelType(TEXT, VOICE)
 */
public class Channel extends AbstractEntity {
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
    //public String getDescription() { return description; }
    public ChannelType getType() { return type; }

    // enum
    public enum ChannelType {
        TEXT,
        VOICE
    }

    // Update
    public void update(String title, String description) {
        this.title = title != null ? title : this.title;
        this.description = description != null ? description : this.description;
        this.setUpdatedAt(System.currentTimeMillis());
    }
}