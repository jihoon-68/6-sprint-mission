package com.sprint.mission.discodeit.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception e, HttpServletRequest request) {
        log.error("Error 발생: Exception: {} URL: {}, Method: {} ",
                e.getMessage(),
                request.getRequestURI(),
                request.getMethod()
        );
        return Map.of("message", e.getMessage());
    }
}
