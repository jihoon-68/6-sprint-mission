package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Instant created;
    private final int age;

    private Instant updated;
    private String password;
    private String username;
    private String email;

    public User(String username, int age, String email) {
        this.id = UUID.randomUUID();
        this.created = setTime();

        this.username = username;
        this.age = age;
        this.email = email;
    }


    public void update(String newUsername, String newEmail, String newPassword) {
        boolean anyValueUpdated = false;
        if (newUsername != null && !newUsername.equals(this.username)) {
            this.username = newUsername;
            anyValueUpdated = true;
        }
        if (newEmail != null && !newEmail.equals(this.email)) {
            this.email = newEmail;
            anyValueUpdated = true;
        }
        if (newPassword != null && !newPassword.equals(this.password)) {
            this.password = newPassword;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updated = Instant.now();
        }
    }

    //유져 본연에 속성이 변경 시에만 업데이트 갱신
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
