package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private UUID id;
  private UUID profileId;
  private Instant createdAt;
  private Instant updatedAt;
  //
  private String username;
  private String email;
  private String password;
  private boolean online;

  public User(String username, String email, String password) {
    this.id = UUID.randomUUID();
    this.createdAt = Instant.now();
    //
    this.username = username;
    this.email = email;
    this.password = password;
    this.online = true;
  }

  public User(String username, String email, String password, UUID profileId) {
    this(username, email, password);
    this.profileId = profileId;
  }

  // 프론트엔드에서 유저이름과 이메일을 같은값으로 수정하면 null로 들어옴. null 체크
  // 비밀번호 같은 값은 그대로 값 들어옴
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
      this.updatedAt = Instant.now();
    }

  }

  public void update(UUID profileId) {
    if (profileId != null && profileId != this.profileId) {
      this.profileId = profileId;
    }
  }

  public void update(boolean online) {
    this.online = online;
  }
}
