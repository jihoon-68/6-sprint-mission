package com.sprint.mission.discodeit.exception.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BaseErrorResponse {
    private String message;
    private String code;
    private Instant timestamp;
    private String path;
}
