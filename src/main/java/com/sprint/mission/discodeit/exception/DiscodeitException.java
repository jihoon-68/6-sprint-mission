package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class DiscodeitException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Instant timestamp;
    private final Map<String, Object> details;

    public DiscodeitException(ErrorCode errorCode, Instant timestamp,
        Map<String, Object> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.timestamp = timestamp;
        this.details = details;
    }
}
