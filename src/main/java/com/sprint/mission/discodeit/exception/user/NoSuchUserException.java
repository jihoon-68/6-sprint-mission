package com.sprint.mission.discodeit.exception.user;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserException;
import java.util.Map;

public class NoSuchUserException extends UserException {

  public NoSuchUserException() {
    super(ErrorCode.NO_SUCH_USER, Map.of());
  }

  public NoSuchUserException(Map<String, Object> details) {
    super(ErrorCode.NO_SUCH_USER, details);
  }
}
