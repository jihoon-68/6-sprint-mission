package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "user_name")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseUpdatableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Override
    public UUID getId() {
        return id;
    }

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "profile_id", referencedColumnName = "id",
            columnDefinition = "BINARY(16)", nullable = true)
    private BinaryContent profile;

    @Setter
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserStatus userStatus;

    public Boolean getOnline() {
        return userStatus != null && userStatus.isOnline();
    }

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
