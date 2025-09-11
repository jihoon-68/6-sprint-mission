package com.sprint.mission.discodeit.entity;

import org.springframework.lang.Nullable;

import java.io.Serial;
import java.time.Instant;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2L;

    private static final String USER_NAME_CANNOT_BE_BLANK = "User name cannot be blank.";
    private static final String USER_MAIL_PATTERN_IS_INVALID = "User mail pattern is invalid: '%s'";
    private static final String USER_NICKNAME_CANNOT_BE_BLANK = "User nickname cannot be blank.";
    private static final String USER_PASSWORD_CANNOT_BE_BLANK = "User password cannot be blank.";

    private static final Pattern mailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final String name;
    private final String mail;
    private final String nickname;
    private final String password;

    public User(
            @Nullable UUID id,
            @Nullable Instant createdAt,
            @Nullable Instant updatedAt,
            String name,
            String mail,
            String nickname,
            String password
    ) {
        super(id, createdAt, updatedAt);
        if (name.isBlank()) {
            throw new IllegalArgumentException(USER_NAME_CANNOT_BE_BLANK);
        }
        if (!mailPattern.matcher(mail).matches()) {
            throw new IllegalArgumentException(USER_MAIL_PATTERN_IS_INVALID.formatted(mail));
        }
        if (nickname.isBlank()) {
            throw new IllegalArgumentException(USER_NICKNAME_CANNOT_BE_BLANK);
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException(USER_PASSWORD_CANNOT_BE_BLANK);
        }
        this.name = name.trim();
        this.mail = mail.trim();
        this.nickname = nickname.trim();
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
