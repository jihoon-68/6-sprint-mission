package com.sprint.mission.discodeit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String nullPointerExceptionHandler(NullPointerException exception) {

        System.out.println("Global 레벨의 exception 처리");

        return "error/nullPointer";
    }

//    @ExceptionHandler(MemberRegistException.class)
//    public String userExceptionHandler(Model model, MemberRegistException exception) {
//
//        System.out.println("Global 레벨의 exception 처리");
//        model.addAttribute("exception", exception);     //exception.getMessage()가 html에서 ${ exception.message }로 처리
//
//        return "error/memberRegist";
//    }

    // default exception
    @ExceptionHandler(Exception.class)
    public String nullPointerExceptionHandler(Exception exception) {
        return "error/default";
    }

}
