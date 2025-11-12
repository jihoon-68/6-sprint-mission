package com.sprint.mission.discodeit.exception.userstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserStatusException;
import java.util.Map;

public class NoSuchUserStatusException extends UserStatusException {

  public NoSuchUserStatusException() {
    super(ErrorCode.NO_SUCH_USER_STATUS, Map.of());
  }

  public NoSuchUserStatusException(Map<String, Object> details) {
    super(ErrorCode.NO_SUCH_USER_STATUS, details);
  }

}
