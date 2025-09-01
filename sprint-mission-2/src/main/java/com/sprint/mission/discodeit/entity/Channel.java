package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 3L;
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
        this.created = setTime();
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
        this.updated = setTime();
    }
    public void updateChannelRoot(User root) {
        this.root = root;
        this.updated = setTime();
    }

    //서버에 본연에 속성이 변경 시에만 업데이트 갱신
    public void updateChanelUsers(User users) {
        this.users.add(users);
    }
    public void updateChanelMessages(Message messages) {
        this.messages.add(messages);
    }

    public String toString(){
        return "서버 정보: " + "\n" +
                "서버 ID: " + this.id + "\n" +
                "서버 이름: " + this.channelName + "\n" +
                "서버 관리자: " + this.root.getUsername() + "\n" +
                "서버 인원: " + this.users + "\n" +
                "서버 메세지: " + this.messages + "\n"+
                "서버 생성일자: " + this.channelName + "\n" +
                "서버 수정일자: " + this.created + "\n";

    }

}
