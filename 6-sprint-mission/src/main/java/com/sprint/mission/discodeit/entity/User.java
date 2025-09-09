package com.sprint.mission.discodeit.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID profileId;
    private Long createdAt;
    private Long updatedAt;
    //
    private String username;
    private String email;
    private String password;
    private String imagePath;
    private File image;

    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now().getEpochSecond();
        //
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String imagePath, BinaryContent binaryContent){
        this(username, email, password);
        this.imagePath = imagePath;
        this.image = binaryContent.getImage();
        this.profileId = binaryContent.getId();
    }

    public void update(String newUsername, String newEmail, String newPassword, String imagePath) {
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
            this.updatedAt = Instant.now().getEpochSecond();
        }

        if (imagePath != null){
            this.imagePath = imagePath;
        }
    }
}
