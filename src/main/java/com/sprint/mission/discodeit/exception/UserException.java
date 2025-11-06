package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class UserException extends DiscodeitException {

    public UserException(ErrorCode errorCode, Instant timestamp,
        Map<String, Object> details) {
        super(errorCode, timestamp, details);
    }
}
