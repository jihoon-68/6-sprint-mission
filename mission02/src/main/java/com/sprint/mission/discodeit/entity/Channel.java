package com.sprint.mission.discodeit.entity;

public class Channel extends Common{
    private String channelName;
    private String channelDescription;

    public Channel(String channelName, String channelDescription) {
        super();
        if(channelName == null || channelName.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 이름은 비어 있을 수 없습니다.");
        }
        if(channelDescription == null) {
            throw new IllegalArgumentException("채널 설명은 빈칸일 수 없습니다.");
        }
        this.channelName = channelName;
        this.channelDescription = channelDescription;
    }

    public String getChannelName() {return channelName;}
    public void setChannelName(String channelName) {
        if(channelName == null || channelName.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 이름은 비어 있을 수 없습니다.");
        }
        this.channelName = channelName;
    }

    public String getChannelDescription() {return channelDescription;}
    public void setChannelDescription(String channelDescription) {
        if(channelDescription == null) {
            throw new IllegalArgumentException("채널 설명은 빈칸일 수 없습니다.");
        }
        this.channelDescription = channelDescription;
    }

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
