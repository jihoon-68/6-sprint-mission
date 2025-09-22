package com.sprint.mission.discodeit.user.domain;

import com.sprint.mission.discodeit.common.exception.DiscodeitException;

public sealed class UserException extends DiscodeitException {

    public UserException(String message) {
        super(message);
    }

    public static final class BlankUserNicknameException extends UserException {

        public BlankUserNicknameException() {
            super("User nickname cannot be blank.");
        }
    }

    public static final class BlankUserPasswordException extends UserException {

        public BlankUserPasswordException() {
            super("User password cannot be blank.");
        }
    }

    public static final class BalnkUserNameExcpetion extends UserException {

        public BalnkUserNameExcpetion() {
            super("User name cannot be blank");
        }
    }

    public static final class InvalidUserEmailFormatException extends UserException {

        public InvalidUserEmailFormatException(String mail) {
            super("User mail pattern is invalid: '%s'".formatted(mail));
        }
    }
}
