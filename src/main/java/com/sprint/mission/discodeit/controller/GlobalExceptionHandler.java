package com.sprint.mission.discodeit.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        saveLog(ex, request);
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchElementException.class )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        saveLog(ex, request);
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> handleException(Exception e, HttpServletRequest request) {

        saveLog(e, request);
        return Map.of("message", e.getMessage());
    }

    private void saveLog(Exception e, HttpServletRequest request)
    {
        log.error("Error message : {}, url : {}, trace : {}", e.getMessage(), request.getRequestURL().toString(), e);
    }
}
