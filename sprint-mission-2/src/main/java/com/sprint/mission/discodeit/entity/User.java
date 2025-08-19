package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private int age;
    private String email;
    private List<User> friends;
    private List<Channel>  channels;
    private Long created;
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
}
