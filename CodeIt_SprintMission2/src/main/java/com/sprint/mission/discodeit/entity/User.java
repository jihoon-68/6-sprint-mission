package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Objects;

public class User extends Common implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;

    public User(String userName, String userEmail){
        super();
        this.name = name;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public void updateUser(String newName, String newEmail){
        boolean nameChanged = (newName != null) && !Objects.equals(this.name, newName);
        boolean emailChanged = (newEmail != null) && !Objects.equals(this.email, newEmail);

        if (!nameChanged && !emailChanged) {
            System.out.println("Username and Email are not change");
            return;
        }

        if (nameChanged) this.name = newName;
        if (emailChanged) this.email = newEmail;
        this.setUpdatedAt();
    }



    @Override
    public String toString() {
        return "User {" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdateAt() +
                '}';
    }
}
