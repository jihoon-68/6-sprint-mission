package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Channel extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<User> users = new ArrayList<>();
    private String name;
    private List<Message> messages = new ArrayList<>();

    public Channel(String name, List<User> users, List<Message> messages) {
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

    public void update(String name) {
        if (name != null) this.name = name;
        super.updatedAt();
    }
}
