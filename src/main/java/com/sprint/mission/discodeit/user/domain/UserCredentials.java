package com.sprint.mission.discodeit.user.domain;

import com.sprint.mission.discodeit.user.domain.UserException.BlankUserNicknameException;
import com.sprint.mission.discodeit.user.domain.UserException.BlankUserPasswordException;

import java.io.Serial;
import java.io.Serializable;

public record UserCredentials(
        String nickname,
        String password
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserCredentials {
        if (nickname.isBlank()) {
            throw new BlankUserNicknameException();
        }
        if (password.isBlank()) {
            throw new BlankUserPasswordException();
        }
        nickname = nickname.trim();
    }
}
