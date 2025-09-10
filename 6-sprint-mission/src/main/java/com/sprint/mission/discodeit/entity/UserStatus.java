package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private UUID userid;
    private String isOnline;
    private Long updatedAt;

    public UserStatus(UUID userid) {
        this.id = UUID.randomUUID();
        this.userid = userid;
        this.isOnline = "Offline";
        this.updatedAt = Instant.now().getEpochSecond();
    }

    public boolean CheckOnline() {
        if(this.updatedAt - Instant.now().getEpochSecond()<= 300){
            this.isOnline = "Online";
            return true;
        }
        return false;
    }

    public void update() {
        boolean anyValueUpdated = CheckOnline();
        if (anyValueUpdated) {
            this.updatedAt = Instant.now().getEpochSecond();
            System.out.println("유저가 온라인입니다");
        } else{
            this.isOnline= "Offline";
            System.out.println("유저가 오프라인입니다");
        }
    }
}
