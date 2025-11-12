package com.sprint.mission.discodeit.exception;

import java.util.Map;

public abstract class UserException extends DiscodeitException {

  public UserException(ErrorCode code) { super(code); }
  public UserException(ErrorCode code, Map<String,Object> details) { super(code, details); }
}