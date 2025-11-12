package com.sprint.mission.discodeit.exception;

import java.time.Instant;

public class UpdatePrivateChannelException extends ChannelException {

    private static final ErrorCode errorCode = ErrorCode.UPDATE_PRIVATE_CHANNEL;

    public UpdatePrivateChannelException(java.util.Map<String, Object> details) {
        super(errorCode, Instant.now(), details);
    }

}
