package com.sprint.mission.discodeit.exception;

public class ReadStatusAlreadyExistException extends ChannelException {

    private static final ErrorCode errorCode = ErrorCode.READ_STATUS_ALREADY_EXISTS;

    public ReadStatusAlreadyExistException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
