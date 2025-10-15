package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseUpdatableEntity {

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "profile_id", referencedColumnName = "id",
      columnDefinition = "BINARY(16)", nullable = true)
  private BinaryContent profile;

  @Setter
  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  private UserStatus userStatus;

  public User(String username, String email, String password, BinaryContent profile) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profile = profile;
  }

  public void update(String newUsername, String newEmail, String newPassword,
      BinaryContent newProfile) {
    if (newUsername != null) {
      this.username = newUsername;
    }
    if (newEmail != null) {
      this.email = newEmail;
    }
    if (newPassword != null) {
      this.password = newPassword;
    }
    if (newProfile != null) {
      this.profile = newProfile;
    }
  }
}
