package com.sprint.mission.discodeit.exception;

public class UserAlreadyExistException extends UserException {

    private static final ErrorCode errorCode = ErrorCode.USER_ALREADY_EXISTS;

    public UserAlreadyExistException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
