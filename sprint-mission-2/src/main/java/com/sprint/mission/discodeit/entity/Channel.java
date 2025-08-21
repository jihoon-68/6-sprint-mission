package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel  implements Serializable{
    private static final long serialVersionUID = 3L;
    private final UUID id;
    private String channelName;
    private String root;
    private List<User> users;
    private List<Message> messages;
    private final Long created;
    private Long updated;

    public Channel(String channelName, String root) {
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
    public String getChannelRoot() {return this.root;}
    public List<User> getChannelUsers() {return this.users;}
    public List<Message> getChannelMessages() {return this.messages;}
    public Long getChannelCreated() {return this.created;}
    public Long getChannelUpdated() {return this.updated;}

    //update
    public void updateChanelName(String channelName) {
        this.channelName = channelName;
        updatedChannel();
    }
    public void updateChannelRoot(String root) {
        this.root = root;
        updatedChannel();
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
                "서버 ID: " + this.getChannelId() + "\n" +
                "서버 이름: " + this.getChannelName() + "\n" +
                "서버 관리자: " + this.getChannelRoot() + "\n" +
                "서버 인원: " + this.getChannelUsers() + "\n" +
                "서버 메세지: " + this.getChannelMessages() + "\n"+
                "서버 생성일: " + this.getChannelCreated() + "\n";

    }

}
