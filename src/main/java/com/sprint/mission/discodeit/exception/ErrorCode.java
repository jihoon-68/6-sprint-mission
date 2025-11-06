package com.sprint.mission.discodeit.exception;

import com.sprint.mission.discodeit.dto.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "The specified user was not found."),
    CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "The specified channel was not found."),
    AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "The specified author was not found."),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "The specified message was not found."),
    USER_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "US001",
        "The specified user status was not found."),
    READ_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "RS001",
        "The specified read status was not found."),
    BINARY_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "BC001",
        "The specified binary content was not found."),
    READ_STATUS_ALREADY_EXISTS(HttpStatus.CONFLICT, "RS002",
        "A read status for the specified user and channel already exists."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "U002",
        "A user with the specified identifier already exists."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "U003",
        "A user with the specified username or email already exists."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U004", "A user with the specified email already exists."),
    UPDATE_PRIVATE_CHANNEL(HttpStatus.FORBIDDEN, "C002", "Private channels cannot be updated."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "G001", "The provided value is invalid.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
