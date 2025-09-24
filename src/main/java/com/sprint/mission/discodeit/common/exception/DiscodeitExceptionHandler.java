package com.sprint.mission.discodeit.common.exception;

import com.sprint.mission.discodeit.common.exception.DiscodeitException.DiscodeitPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DiscodeitExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DiscodeitExceptionHandler.class);

    @ExceptionHandler(DiscodeitPersistenceException.class)
    public ProblemDetail handleDiscodeitPersistenceException(DiscodeitException ex) {
        log.warn("Discodeit persistence error occurred", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DiscodeitException.class)
    public ProblemDetail handleDiscodeitException(DiscodeitException ex) {
        log.warn("Discodeit error occurred", ex);
        return ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(RuntimeException ex) {
        log.warn("Http message not readable", ex);
        return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Method argument not valid", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, Object> errorFieldToMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toUnmodifiableMap(FieldError::getField, FieldError::getDefaultMessage));
        problemDetail.setProperties(errorFieldToMessage);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        if (log.isDebugEnabled()) {
            for (Throwable suppressed : ex.getSuppressed()) {
                log.debug("Suppressed exception", suppressed);
            }
        }
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
