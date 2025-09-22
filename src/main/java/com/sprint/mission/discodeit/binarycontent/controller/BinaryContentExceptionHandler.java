package com.sprint.mission.discodeit.binarycontent.controller;

import com.sprint.mission.discodeit.binarycontent.domain.BinaryContentException;
import com.sprint.mission.discodeit.common.exception.DiscodeitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BinaryContentController.class)
public class BinaryContentExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BinaryContentExceptionHandler.class);

    @ExceptionHandler(BinaryContentException.class)
    public ProblemDetail handleBinaryContentException(DiscodeitException ex) {
        log.warn("BinaryContent error occurred", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
