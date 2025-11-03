package com.sprint.mission.discodeit.exception.readstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;

public class ReadStatusNotFoundException extends ReadStatusException {
    public ReadStatusNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}