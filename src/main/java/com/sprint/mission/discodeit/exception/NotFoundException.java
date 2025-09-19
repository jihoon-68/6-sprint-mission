package com.sprint.mission.discodeit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND) // 예외 해당 시 자동으로 HTTP 404 응답을 반환함.
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L; // RuntimeException은 Serializable이므로 버전 충돌 방지용으로 사용.

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
