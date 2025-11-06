package com.sprint.mission.discodeit.exception;

public class DuplicateEmailException extends UserException {

    private static final ErrorCode errorCode = ErrorCode.DUPLICATE_EMAIL;

    public DuplicateEmailException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
