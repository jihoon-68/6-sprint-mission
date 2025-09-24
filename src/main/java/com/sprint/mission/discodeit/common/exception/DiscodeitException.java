package com.sprint.mission.discodeit.common.exception;

public class DiscodeitException extends RuntimeException {

    public DiscodeitException(String message) {
        super(message);
    }

    public static final class DiscodeitPersistenceException extends DiscodeitException {

        public DiscodeitPersistenceException(String message) {
            super(message);
        }
    }
}
