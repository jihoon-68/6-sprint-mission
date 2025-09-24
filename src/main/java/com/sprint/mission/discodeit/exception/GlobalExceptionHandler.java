package com.sprint.mission.discodeit.exception;

import com.sprint.mission.discodeit.exception.response.BaseErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseErrorResponse> handleNotFoundException(IllegalArgumentException e, HttpServletRequest request) {
        BaseErrorResponse error = new BaseErrorResponse();
        error.setMessage(e.getMessage());
        error.setCode("VALIDATION_ERROR");
        error.setPath(request.getRequestURI());
        error.setTimestamp(Instant.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<BaseErrorResponse> handleNotFoundException(NoSuchElementException e, HttpServletRequest request) {
        BaseErrorResponse error = new BaseErrorResponse();
        error.setMessage(e.getMessage());
        error.setCode("NOT_FOUND_ERROR");
        error.setPath(request.getRequestURI());
        error.setTimestamp(Instant.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
