package com.sprint.mission.discodeit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 예외처리 구성
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 리소스 예외처리
    @ExceptionHandler(NotFoundException.class) // 괄호 안 예외클래스가 발생하면 아래 메서드 실행함.
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 잘못된 요청 예외처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // DTO 검증 실패 (@Valid, @NotNull, @Size 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError() != null ? // 유효성 검사 실패한 (첫번째) 필드 에러가 존재하면
                e.getBindingResult().getFieldError().getDefaultMessage() : // 기본 에러메시지를 가져옴
                "잘못된 요청 데이터입니다."; // 필드 에러는 없고 다른 유효성 검사 실패 시 출력. (기본값 역할)
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(error);
    }

    // 위에서 처리하지 않은 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 내부 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 공통 에러 응답 DTO
    // 에러 응답 형식을 직접 정의해 사용. 에러 코드, 에러메시지를 인자로 받음.
    record ErrorResponse(int status, String message) {}
}
