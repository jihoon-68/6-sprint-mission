package com.sprint.mission.discodeit.entity;

import java.io.Serializable;

public class Channel extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private String channelName;
    private String channelDescription;

    public Channel(String channelName, String channelDescription){
        super();
        this.channelName = channelName;
        this.channelDescription = channelDescription;

    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
        setUpdateAt(System.currentTimeMillis());
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
        setUpdateAt(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelName='" + channelName + '\'' +
                ", channelDescription='" + channelDescription + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdateAt() +
                '}';
    }
}
