package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;

    private String username;
    private String email;
    private String password;
    private UUID profileId;

    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileId = null;
        this.createdAt = Instant.now();
    }


    public void update(UpdateUserDTO updateUserDTO) {
        boolean anyValueUpdated = false;

        if ( updateUserDTO.username() != null
                && !updateUserDTO.username().isEmpty()
                && !updateUserDTO.username().equals(this.username)) {
            this.username = updateUserDTO.username();
            anyValueUpdated = true;
        }

        if ( updateUserDTO.email() != null
                && !updateUserDTO.email().isEmpty()
                && !updateUserDTO.email().equals(this.email)) {
            this.email = updateUserDTO.email();
            anyValueUpdated = true;
        }

        if (  updateUserDTO.password() != null
                && !updateUserDTO.password().isEmpty()
                && !updateUserDTO.password().equals(this.password)) {
            this.password = updateUserDTO.password();
            anyValueUpdated = true;
        }

        if (updateUserDTO.profileId()!= null && !updateUserDTO.profileId().equals(this.profileId)) {
            this.profileId = updateUserDTO.profileId();
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }


    //유져 본연에 속성이 변경 시에만 업데이트 갱신
    @Override
    public String toString(){
        return "유저 정보: "+ "\n" +
                "ID: " + this.id + "\n" +
                "아름: " + this.username + "\n" +
                "이메일: " + this.email + "\n" +
                "계정 생성일자: " + this.createdAt + "\n" +
                "계정 생성일자: " + this.updatedAt + "\n";
    }
}
