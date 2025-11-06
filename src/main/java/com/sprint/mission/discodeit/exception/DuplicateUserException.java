package com.sprint.mission.discodeit.exception;

public class DuplicateUserException extends UserException {

    private static final ErrorCode errorCode = ErrorCode.DUPLICATE_USER;

    public DuplicateUserException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
