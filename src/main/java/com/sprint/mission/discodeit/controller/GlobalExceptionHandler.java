package com.sprint.mission.discodeit.controller;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNullPointerException(NullPointerException ex) {
    log.warn("데이터가 없습니다", ex);
    return Map.of(
        "status", 404,
        "message", "데이터가 없습니다"
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleIllegalArg(IllegalArgumentException ex) {
    log.warn("잘못된 요청", ex);
    return Map.of(
        "status", 400,
        "message", "잘못된 요청"
    );
  }

  // 이외 예외 처리
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleException(Exception ex) {
    log.warn("서버의 잘못된 응답", ex);
    return Map.of(
        "status", 500,
        "message", "서버의 잘못된 응답"
    );
  }

}
