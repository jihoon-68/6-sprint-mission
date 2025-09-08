package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.DTO.User.CreateUserDTO;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant created;
    private final int age;

    private UUID profileId;
    private Instant updated;
    private String password;
    private String username;
    private String email;

    public User(CreateUserDTO createUserDTO) {
        this.id = UUID.randomUUID();
        this.profileId = createUserDTO.profileId() == null ? UUID.randomUUID() : profileId;
        this.created = setTime();
        this.username = createUserDTO.userName();
        this.age = createUserDTO.age();
        this.email = createUserDTO.email();
        this.password = createUserDTO.password();
    }


    public void update(CreateUserDTO createUserDTO) {
        boolean anyValueUpdated = false;
        if (createUserDTO.userName() != null && !createUserDTO.userName().equals(this.username)) {
            this.username = createUserDTO.userName();
            anyValueUpdated = true;
        }

        if (createUserDTO.email() != null && !createUserDTO.email().equals(this.email)) {
            this.email = createUserDTO.email();
            anyValueUpdated = true;
        }

        if (createUserDTO.password() != null && !createUserDTO.password().equals(this.password)) {
            this.password = createUserDTO.password();
            anyValueUpdated = true;
        }

        if (createUserDTO.profileId()!= null && !createUserDTO.profileId().equals(this.profileId)) {
            this.profileId = createUserDTO.profileId();
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updated = setTime();
        }
    }


    //유져 본연에 속성이 변경 시에만 업데이트 갱신
    @Override
    public String toString(){
        return "유저 정보: "+ "\n" +
                "ID: " + this.id + "\n" +
                "아름: " + this.username + "\n" +
                "나이: " + this.age + "\n" +
                "이메일: " + this.email + "\n" +
                "계정 생성일자: " + this.created + "\n" +
                "계정 생성일자: " + this.updated + "\n";
    }
}
