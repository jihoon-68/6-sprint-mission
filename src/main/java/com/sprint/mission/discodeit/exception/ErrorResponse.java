package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse (
        Instant timestamp,
        String code,
        String message,
        Map<String, Object> details,
        String exceptionType, // 예외가 발생한 클래스 이름
        int status // HTTP 상태 코드
){
    public static ErrorResponse of(String code, String message, Map<String, Object> details, String exceptionType, int status) {
        return new ErrorResponse(Instant.now(), code, message, details, exceptionType, status);
    }
}
