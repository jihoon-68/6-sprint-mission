package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;

public class ErrorResponse {

  private final Instant timestamp;
  private final String code;
  private final String message;
  private final Map<String, Object> details;
  private String exceptionType; // 발생한 예외의 클래스 이름
  private int status; // HTTP Status

  protected ErrorResponse(Instant timestamp, String code, String message,
      Map<String, Object> details, final String exceptionType, final int status) {
    this.timestamp = timestamp;
    this.code = code;
    this.message = message;
    this.details = details;
    this.exceptionType = exceptionType;
    this.status = status;
  }

  protected ErrorResponse(final String code, final String message,
      final Map<String, Object> details, final String exceptionType, final int status) {
    this(Instant.now(), code, message, details, exceptionType, status);
  }

  public static ErrorResponse of(DiscodeitException ex) {
    return new ErrorResponse(
        ex.errorCode.getCode(),
        ex.errorCode.getMessage(),
        ex.details,
        ex.getClass().getSimpleName(),
        ex.errorCode.getStatus().value()
    );
  }

  public static ErrorResponse of(Throwable ex, ErrorCode code) {
    return new ErrorResponse(
        code.getCode(),
        code.getMessage(),
        null,
        ex.getClass().getSimpleName(),
        code.getStatus().value()
    );
  }
}
