package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.List;

public class User extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private transient String password;
    private String nickname = null;
    private String description;
    private String activeType;
    private List<String> badges;

    public User(String name, String password, String nickname, String description, String activeType, List<String> badges) {
        super();
        this.activeType = activeType;
        this.name = name;
        this.password = password;
        this.nickname = (nickname == null) ? name : nickname;
        this.description = description;
        this.badges = badges;
    }

    public String getName() {
        return name;
    }

    public String getActiveType() {
        return activeType;
    }

    public String getDescription() {
        return description;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void update(String name, String nickname) {
        if (name != null) this.name = name;
        if (nickname != null) this.nickname = nickname;
        super.updatedAt();
    }
}


