package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public record User(
        UUID id,
        Long createdAt,
        Long updatedAt,
        String name,
        String mail,
        String nickname
) implements Serializable {

    private static final Pattern mailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Serial
    private static final long serialVersionUID = 1L;

    public User(
            UUID id,
            Long createdAt,
            Long updatedAt,
            String name,
            String mail,
            String nickname
    ) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id: 'null'");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid name: '%s'".formatted(name));
        }
        if (mail == null || !mailPattern.matcher(mail).matches()) {
            throw new IllegalArgumentException("Invalid mail: '%s'".formatted(mail));
        }
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("Invalid nickname: '%s'".formatted(nickname));
        }
        this.id = id;
        this.createdAt = Objects.requireNonNullElseGet(createdAt, () -> Instant.now().toEpochMilli());
        this.updatedAt = Objects.requireNonNullElse(updatedAt, createdAt);
        this.name = name.trim();
        this.mail = mail.trim();
        this.nickname = nickname.trim();
    }

    public static User of(String name, String mail, String nickname) {
        long now = Instant.now().toEpochMilli();
        return new User(
                UUID.randomUUID(),
                now,
                now,
                name,
                mail,
                nickname
        );
    }

    public User updateName(String newName) {
        return new User(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                newName,
                mail,
                nickname
        );
    }

    public User updateMail(String newMail) {
        return new User(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                name,
                newMail,
                nickname
        );
    }

    public User updateNickname(String newNickname) {
        return new User(
                id,
                createdAt,
                Instant.now().toEpochMilli(),
                name,
                mail,
                newNickname
        );
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
