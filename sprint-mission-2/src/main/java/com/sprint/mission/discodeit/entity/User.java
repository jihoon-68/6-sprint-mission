package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID id;
    private String username;
    private final int age;
    private String email;
    private List<User> friends;
    private List<Channel>  channels;
    private final Long created;
    private Long updated;

    public User(String username, int age, String email) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.age = age;
        this.email = email;
        this.friends = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.created = System.currentTimeMillis();
        this.updated = System.currentTimeMillis();
    }
    //유저에 변경이 생기면 업데이트 시간 변경
    public void updatedChannel() {
        this.updated = System.currentTimeMillis();
    }

    //Getter 생성
    public UUID getUserId() {return this.id;}
    public String getUsername() {return this.username;}
    public int getAge() {return this.age;}
    public String getEmail() {return this.email;}
    public List<User> getFriends() {return this.friends;}
    public List<Channel> getChannels() {return this.channels;}
    public Long getCreated() {return this.created;}
    public Long getUpdated() {return this.updated;}

    //update
    public void updateedUsername(String username) {
        this.username = username;
        updatedChannel();
    }

    public  void updateEmail(String email) {
        this.email = email;
        updatedChannel();
    }

    //유져 본연에 속성이 변경 시에만 업데이트 갱신
    public void updatedFriends(User friend) {
        this.friends.add(friend);
    }

    public void updatedChannels(Channel channel) {
        this.channels.add(channel);
    }

    public String toString(){
        return "유저 정보: "+ "\n" +
                "ID: " + this.id + "\n" +
                "아름: " + this.username + "\n" +
                "나이: " + this.age + "\n" +
                "이메일: " + this.email + "\n" +
                "계정 생성일: " + this.created + "\n" +
                "친구: " + this.friends + "\n" +
                "입장 채널: " + this.channels + "\n";
    }
}
