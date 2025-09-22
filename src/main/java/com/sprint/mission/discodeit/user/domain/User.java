package com.sprint.mission.discodeit.user.domain;

import com.sprint.mission.discodeit.common.persistence.BaseEntity;
import com.sprint.mission.discodeit.user.domain.UserException.BalnkUserNameExcpetion;
import com.sprint.mission.discodeit.user.domain.UserException.InvalidUserEmailFormatException;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3L;

    private static final Pattern mailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final UserCredentials userCredentials;
    private final UserStatus userStatus;
    private final String name;
    private final String mail;

    private User(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            UserCredentials userCredentials,
            UserStatus userStatus,
            String name,
            String mail
    ) {
        super(id, createdAt, updatedAt);
        if (name.isBlank()) {
            throw new BalnkUserNameExcpetion();
        }
        if (!mailPattern.matcher(mail).matches()) {
            throw new InvalidUserEmailFormatException(mail);
        }
        this.userCredentials = userCredentials;
        this.userStatus = userStatus;
        this.name = name.trim();
        this.mail = mail.trim();
    }

    public static User of(
            UserCredentials userCredentials,
            UserStatus userStatus,
            String name,
            String mail
    ) {
        return new User(
                null,
                null,
                null,
                userCredentials,
                userStatus,
                name,
                mail
        );
    }

    public User withUserCredentials(UserCredentials userCredentials) {
        return new User(
                this.getId(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                userCredentials,
                this.userStatus,
                this.name,
                this.mail
        );
    }

    public User withUserStatus(UserStatus userStatus) {
        return new User(
                this.getId(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.userCredentials,
                userStatus,
                this.name,
                this.mail
        );
    }

    public User with(String name, String mail) {
        return new User(
                this.getId(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.userCredentials,
                this.userStatus,
                name,
                mail
        );
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "userCredentials=" + userCredentials +
                ", userStatus=" + userStatus +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                "} " + super.toString();
    }
}
