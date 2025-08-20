package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Channel extends Common{
    private String channelName;
    private String channelDescription;

    public Channel(UUID channelId, Long createdAt, String channelName, String channelDescription) {
        super(channelId, createdAt);
        this.channelName = channelName;
        this.channelDescription = channelDescription;
    }

    public String getChannelName() {return channelName;}
    public void setChannelName(String channelName) {this.channelName = channelName;}

    public String getChannelDescription() {return channelDescription;}
    public void setChannelDescription(String channelDescription) {this.channelDescription = channelDescription;}

    @Override
    public String toString() {
        return "Channel{" +
                "channelName='" + channelName + '\'' +
                ", channelDescription='" + channelDescription + '\'' +
                ", uuid=" + getUuid() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
