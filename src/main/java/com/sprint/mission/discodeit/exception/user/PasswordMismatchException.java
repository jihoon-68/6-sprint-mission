package com.sprint.mission.discodeit.exception.user;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserException;
import java.util.Map;

public class PasswordMismatchException extends UserException {

  public PasswordMismatchException() {
    super(ErrorCode.PASSWORD_MISMATCH, Map.of());
  }

  public PasswordMismatchException(Map<String, Object> details) {
    super(ErrorCode.PASSWORD_MISMATCH, details);
  }

}
