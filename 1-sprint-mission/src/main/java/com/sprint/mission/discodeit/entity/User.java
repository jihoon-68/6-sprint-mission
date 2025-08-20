package com.sprint.mission.discodeit.entity;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

public class User extends Common {
    private String activeType;
    private String name;
    private String nickname = null;
    private String description;
    private String[] badges;

    public User(String activeType, String name, String nickname, String description, String[] badges) {
        super();
        this.activeType = activeType;
        this.name = name;
        this.nickname = nickname;
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

    public String[] getBadges() {
        return badges;
    }

    public void update(String name, String nickname) {
        if (name != null) this.name = name;
        if (nickname != null) this.nickname = nickname;
        super.updatedAt();
    }
}


