package com.sprint.mission.discodeit.entity;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.UUID;

public class Channel extends Common {
    private List<User> users = new ArrayList<>();
    private String name;
    private List<Message> messages = new ArrayList<>();

    public Channel(List<User> users, String name, List<Message> messages) {
        super();
        this.users = users;
        this.name = name;
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void update(String name) {
        if (name != null) this.name = name;
        super.updatedAt();
    }
}
