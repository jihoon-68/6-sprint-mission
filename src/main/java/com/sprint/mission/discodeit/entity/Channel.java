package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel extends Common implements Serializable {

    private String channelName;
    //버전관리를 통해 호환성 확보
    private static final long serialVersionUID = 1L;


    public Channel(String channelName) {
        super();
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void updateChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "updatedAt=" + updatedAt +
                ", createAt=" + createAt +
                ", id=" + id +
                ", channelName='" + channelName + '\'' +
                '}';
    }
}
