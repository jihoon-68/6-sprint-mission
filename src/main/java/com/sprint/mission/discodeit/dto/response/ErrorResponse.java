package com.sprint.mission.discodeit.dto.response;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private Instant timestamp;
    private String code;
    private String message;
    private Map<String, Object> details;
    private String exceptionType;
    private int status;

}
