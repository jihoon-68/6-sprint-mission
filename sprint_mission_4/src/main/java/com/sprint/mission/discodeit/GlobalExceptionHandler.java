//package com.sprint.mission.discodeit.controller;
//
////import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.*;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@RestControllerAdvice   // 전역 + JSON
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    // 2-1) @Valid 바디 검증 실패 (DTO 필드 에러)
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            org.springframework.web.context.request.WebRequest request) {
//
//        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//        pd.setTitle("Validation Failed");
//        pd.setDetail("요청 본문이 검증을 통과하지 못했습니다.");
//        // 필드 에러 목록을 내려주고 싶으면:
//        var errors = ex.getBindingResult().getFieldErrors().stream()
//                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
//                .toList();
//        pd.setProperty("errors", errors);
//        return handleExceptionInternal(ex, pd, headers, status, request);
//    }
//
//    // 2-2) 파라미터(@RequestParam/@PathVariable) 검증 실패
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex) {
//        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//        pd.setTitle("Constraint Violation");
//        pd.setDetail(ex.getMessage());
//        return ResponseEntity.badRequest().body(pd);
//    }
//
//    // 2-3) JSON 파싱/형변환 오류
//    @ExceptionHandler({
//            org.springframework.http.converter.HttpMessageNotReadableException.class,
//            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class
//    })
//    public ResponseEntity<ProblemDetail> handleBadJson(Exception ex) {
//        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//        pd.setTitle("Malformed Request");
//        pd.setDetail("요청 본문/타입을 읽을 수 없습니다.");
//        return ResponseEntity.badRequest().body(pd);
//    }
//
//    // 2-4) 업로드 용량 초과
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<ProblemDetail> handleUploadTooLarge(MaxUploadSizeExceededException ex) {
//        var pd = ProblemDetail.forStatus(HttpStatus.PAYLOAD_TOO_LARGE);
//        pd.setTitle("Upload Too Large");
//        pd.setDetail("허용된 업로드 용량을 초과했습니다.");
//        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(pd);
//    }
//
//    // 2-5) 도메인 커스텀 예외 (예: UserNotFoundException)
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ProblemDetail> handleUserNotFound(UserNotFoundException ex) {
//        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
//        pd.setTitle("User Not Found");
//        pd.setDetail(ex.getMessage());
//        pd.setProperty("code", "USER_NOT_FOUND"); // 내부 에러 코드
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
//    }
//
//    // 2-6) 그 외 처리 안 된 예외(최후 방어)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex) {
//        // TODO: 여기서 로그 남기기 (error 레벨)
//        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        pd.setTitle("Internal Server Error");
//        pd.setDetail("예상치 못한 오류가 발생했습니다.");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
//    }
//}
