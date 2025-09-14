package com.sprint.mission.entity;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Channel extends EntityCommon {


    private String channelName;
    private String channelDescription;
    private String channelType;
    private List<Message> messages;
    private List<User> members;

    public Channel(String channelName, String channelDescription) {
        super();
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelType = "PUBLIC";
        this.messages = new ArrayList<>();
        this.members = null;
    }

    public Channel(String channelName, String channelDescription, List<User> members) {
        super();
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelType = "PRIVATE";
        this.messages = new ArrayList<>();
        this.members = members;
    }

}