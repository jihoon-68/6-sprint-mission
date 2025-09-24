package com.sprint.mission.discodeit.message.domain;

import com.sprint.mission.discodeit.common.exception.DiscodeitException;

public sealed class MessageException extends DiscodeitException {

    public MessageException(String message) {
        super(message);
    }

    public static final class BlankMessageContentException extends MessageException {

        public BlankMessageContentException() {
            super("Message content cannot be blank.");
        }
    }
}
