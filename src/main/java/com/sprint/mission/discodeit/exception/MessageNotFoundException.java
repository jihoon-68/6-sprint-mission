package com.sprint.mission.discodeit.exception;

public class MessageNotFoundException extends MessageException {

    private static final ErrorCode errorCode = ErrorCode.MESSAGE_NOT_FOUND;

    public MessageNotFoundException(java.util.Map<String, Object> details) {
        super(errorCode, java.time.Instant.now(), details);
    }

}
