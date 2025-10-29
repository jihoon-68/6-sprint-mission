package com.sprint.mission.discodeit.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNullPointerException(
            NullPointerException ex,
            Model model) {
            model.addAttribute("errormessage","서버스 처리중 오류가 발생 했습니다.");
            model.addAttribute("errorCode", "ERR-400-NPE");
            model.addAttribute("timestamp", LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleException(
            NoSuchElementException ex,
            Model model,
            HttpServletRequest request
    ){
        model.addAttribute("errormessage","대이터 찾는 중에 오류가 발생했습니다.");
        model.addAttribute("errorCode", "ERR-404-NPE");
        model.addAttribute("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
