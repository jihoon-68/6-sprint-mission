package com.sprint.mission.discodeit.exception;

public class ReadStatusNotFoundException extends ChannelException {

    private static final ErrorCode errorCode = ErrorCode.READ_STATUS_NOT_FOUND;

    public ReadStatusNotFoundException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
