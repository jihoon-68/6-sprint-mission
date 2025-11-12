package com.sprint.mission.discodeit.exception;

public class BinaryContentNotFoundException extends BinaryContentException {

    private static final ErrorCode errorCode = ErrorCode.BINARY_CONTENT_NOT_FOUND;

    public BinaryContentNotFoundException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
