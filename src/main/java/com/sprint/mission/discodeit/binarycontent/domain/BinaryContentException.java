package com.sprint.mission.discodeit.binarycontent.domain;

import com.sprint.mission.discodeit.common.exception.DiscodeitException;

public sealed class BinaryContentException extends DiscodeitException {

    public BinaryContentException(String message) {
        super(message);
    }

    public static final class EmptyBinaryContentException extends BinaryContentException {

        public EmptyBinaryContentException() {
            super("BinaryContent cannot be empty");
        }
    }
}
