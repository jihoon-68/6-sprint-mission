package com.sprint.mission.discodeit.exception;

public class UserStatusNotFoundException extends UserException {

    private static final ErrorCode errorCode = ErrorCode.USER_STATUS_NOT_FOUND;

    public UserStatusNotFoundException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
