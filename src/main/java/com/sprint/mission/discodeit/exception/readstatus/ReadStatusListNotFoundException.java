package com.sprint.mission.discodeit.exception.readstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.util.Map;
import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReadStatusListNotFoundException extends ReadStatusException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ReadStatusListNotFoundException(UUID userId) {
        super(
                ErrorCode.READ_STATUS_NOT_FOUND,
                "해당 사용자에 대해 ReadStatus가 하나도 없습니다. userId=" + userId,
                Map.of("userId", userId)
        );
    }
}
