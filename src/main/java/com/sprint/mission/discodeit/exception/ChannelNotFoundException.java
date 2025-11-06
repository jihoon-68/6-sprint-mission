package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class ChannelNotFoundException extends ChannelException {

    private static final ErrorCode errorCode = ErrorCode.CHANNEL_NOT_FOUND;

    public ChannelNotFoundException(Map<String, Object> details) {
        super(errorCode, Instant.now(), details);
    }
}
