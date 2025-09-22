package com.sprint.mission.discodeit.message.controller;

import com.sprint.mission.discodeit.common.exception.DiscodeitException;
import com.sprint.mission.discodeit.message.domain.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MessageController.class)
public class MessageExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageExceptionHandler.class);

    @ExceptionHandler(MessageException.class)
    public ProblemDetail handleMessageException(DiscodeitException ex) {
        log.warn("Message error occurred", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
