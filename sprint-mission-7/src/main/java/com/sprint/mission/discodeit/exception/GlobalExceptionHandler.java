package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DiscodeitException.class)
  public ResponseEntity<ErrorResponse> handleDiscodeitException(DiscodeitException ex) {
    // 로깅 레벨은 상황에 따라: 클라이언트 실수성(BAD_REQUEST, NOT_FOUND 등)은 warn, 서버 버그는 error
    if (ex.errorCode.getStatus().is4xxClientError()) {
      log.warn("[{}] {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
    } else {
      log.error("[{}] {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
    }

    ErrorResponse body = ErrorResponse.of(ex); // 상태코드는 ErrorCode 안에 이미 있음
    return ResponseEntity.status(ex.errorCode.getStatus()).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, Object> details = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            FieldError::getDefaultMessage,
            (msg1, msg2) -> msg1  // 중복 필드 시 첫 메시지만 사용
        ));

    ErrorResponse response = new ErrorResponse(
        "VALIDATION_FAILED",
        "요청 데이터가 유효하지 않습니다.",
        details,
        ex.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST.value()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
    var dc = ErrorCode.BAD_REQUEST;
    return ResponseEntity
        .status(dc.getStatus())
        .body(ErrorResponse.of(ex, dc));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleException(NoSuchElementException e) {
    e.printStackTrace();
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    e.printStackTrace();
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(e.getMessage());
  }
}
