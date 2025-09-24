package com.sprint.mission.discodeit.channel.controller;

import com.sprint.mission.discodeit.channel.domain.ChannelException;
import com.sprint.mission.discodeit.common.exception.DiscodeitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ChannelController.class)
public class ChannelExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ChannelExceptionHandler.class);
    
    @ExceptionHandler(ChannelException.class)
    public ProblemDetail handleChannelException(DiscodeitException ex) {
        log.warn("Channel error occurred", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
