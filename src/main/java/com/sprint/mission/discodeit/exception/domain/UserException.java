package com.sprint.mission.discodeit.exception.domain;

import com.sprint.mission.discodeit.exception.DiscodeitException;
import com.sprint.mission.discodeit.exception.ErrorCode;

public class UserException extends DiscodeitException {

  public UserException(ErrorCode errorCode,  java.util.Map<String, Object> details) {
    super(errorCode, details);
  }
}
