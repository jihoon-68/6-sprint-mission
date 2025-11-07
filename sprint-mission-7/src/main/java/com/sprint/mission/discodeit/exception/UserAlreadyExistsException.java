package com.sprint.mission.discodeit.exception;

import java.util.Map;


public class UserAlreadyExistsException extends UserException {

  public UserAlreadyExistsException(String username) {
    super(
        ErrorCode.DUPLICATE_USER,
        Map.of("username", username));
  }
}