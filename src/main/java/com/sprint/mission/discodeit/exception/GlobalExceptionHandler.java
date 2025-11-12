package com.sprint.mission.discodeit.exception;

import com.sprint.mission.discodeit.dto.response.ErrorResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
        log.error("AuthException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        log.error("AuthorNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(BinaryContentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBinaryContentNotFoundException(
        BinaryContentNotFoundException ex) {
        log.error("BinaryContentNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(ChannelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleChannelNotFoundException(
        ChannelNotFoundException ex) {
        log.error("ChannelNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(
        DuplicateEmailException ex) {
        log.error("DuplicateEmailException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(
        DuplicateUserException ex) {
        log.error("DuplicateUserException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotFoundException(
        MessageNotFoundException ex) {
        log.error("MessageNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(ReadStatusAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleReadStatusAlreadyExistException(
        ReadStatusAlreadyExistException ex) {
        log.error("ReadStatusAlreadyExistException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(ReadStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReadStatusNotFoundException(
        ReadStatusNotFoundException ex) {
        log.error("ReadStatusNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(UpdatePrivateChannelException.class)
    public ResponseEntity<ErrorResponse> handleUpdatePrivateChannelException(
        UpdatePrivateChannelException ex) {
        log.error("UpdatePrivateChannelException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(
        UserAlreadyExistException ex) {
        log.error("UserAlreadyExistException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("UserNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(UserStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserStatusNotFoundException(
        UserStatusNotFoundException ex) {
        log.error("UserStatusNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(ex.getMessage())
            .code(ex.getErrorCode().getCode())
            .timestamp(ex.getTimestamp())
            .details(ex.getDetails())
            .exceptionType(ex.getErrorCode().getClass().getSimpleName())
            .status(ex.getErrorCode().getHttpStatus().value())
            .build();
        return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
        MethodArgumentNotValidException ex) {
        log.error("Validation exception occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message("Validation failed for one or more arguments.")
            .code("G002")
            .timestamp(java.time.Instant.now())
            .details(Map.of("errors", ex.getBindingResult().getAllErrors()))
            .exceptionType(ex.getClass().getSimpleName())
            .status(org.springframework.http.HttpStatus.BAD_REQUEST.value())
            .build();
        return new ResponseEntity<>(errorResponse, org.springframework.http.HttpStatus.BAD_REQUEST);
    }
}
