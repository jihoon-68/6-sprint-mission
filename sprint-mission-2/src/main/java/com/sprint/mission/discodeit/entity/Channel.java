package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel {
    private UUID id;
    private String channelName;
    private User root;
    private List<User> users;
    private List<Message> messages;
    private Long created;
    private Long updated;

    public Channel(String channelName, User root) {
        this.id = UUID.randomUUID();
        this.channelName = channelName;
        this.root = root;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
    }
}
