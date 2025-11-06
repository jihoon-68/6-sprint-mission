package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class UserNotFoundException extends UserException {

    private static final ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException(Map<String, Object> details) {
        super(errorCode, Instant.now(), details);
    }
}
