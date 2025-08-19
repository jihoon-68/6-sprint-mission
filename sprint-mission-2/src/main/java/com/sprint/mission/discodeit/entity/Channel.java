package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel {
    private final UUID id;
    private String channelName;
    private User root;
    private List<User> users;
    private List<Message> messages;
    private final Long created;
    private Long updated;

    public Channel(String channelName, User root) {
        this.id = UUID.randomUUID();
        this.channelName = channelName;
        this.root = root;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.created = System.currentTimeMillis();
    }

    //서버에 변경이 생기면 업데이트 시간 변경
    public void updatedChannel() {
        this.updated = System.currentTimeMillis();
    }

    //Getter
    public UUID getChannelId() {return this.id;}
    public String getChannelName() {return this.channelName;}
    public User getChannelRoot() {return this.root;}
    public List<User> getChannelUsers() {return this.users;}
    public List<Message> getChannelMessages() {return this.messages;}
    public Long getChannelCreated() {return this.created;}
    public Long getChannelUpdated() {return this.updated;}

    //update
    public void updateChanelName(String channelName) {
        this.channelName = channelName;
        updatedChannel();
    }
    public void updateChannelRoot(User root) {
        this.root = root;
        updatedChannel();
    }

    //서버에 본연에 속성이 변경 시에만 업데이트 갱신
    public void updateChanelUsers(List<User> users) {
        this.users = users;
    }
    public void updateChanelMessages(List<Message> messages) {
        this.messages = messages;
    }

}
