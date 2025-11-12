package com.sprint.mission.discodeit.exception;

public class AuthorNotFoundException extends MessageException {

    private static final ErrorCode errorCode = ErrorCode.AUTHOR_NOT_FOUND;

    public AuthorNotFoundException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }
}
