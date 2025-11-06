package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class MessageException extends DiscodeitException {

    public MessageException(ErrorCode errorCode, Instant timestamp, Map<String, Object> details) {
        super(errorCode, timestamp, details);
    }
}
