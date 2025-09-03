package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.*;

public class User extends Common implements Serializable {

    private String userName;
    private String email;
    //버전관리를 통해 호환성 확보
    private static final long serialVersionUID = 1L;

    public User(String userName, String email) {
        super();
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", createAt=" + createAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
