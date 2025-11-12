package com.sprint.mission.discodeit.exception;

public class BinaryContentException extends DiscodeitException {

    public BinaryContentException(ErrorCode errorCode, java.time.Instant timestamp,
        java.util.Map<String, Object> details) {
        super(errorCode, timestamp, details);
    }

}
