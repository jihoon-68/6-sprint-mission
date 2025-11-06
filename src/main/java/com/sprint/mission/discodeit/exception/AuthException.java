package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class AuthException extends UserException {

    private static final ErrorCode errorCode = ErrorCode.INVALID_VALUE;

    public AuthException(Map<String, Object> details) {
        super(errorCode, Instant.now(), details);
    }
}
