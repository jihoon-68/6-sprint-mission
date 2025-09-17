package com.sprint.mission.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User extends EntityCommon{
    private String username;
    private String email;
    private String password;
    private UserStatus status;
    private UUID profileId;
    private List<Message> messages;

    public User(String username, String email, String password, UUID profileId) {

        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = new UserStatus(super.getId(), true, Instant.now());
        this.profileId = profileId;
        this.messages = new ArrayList<>();

    }
}
