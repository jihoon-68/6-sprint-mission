package com.sprint.mission.discodeit.exception.userstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserStatusException;
import java.util.Map;

public class AllReadyExistUserStatusException extends UserStatusException {

  public AllReadyExistUserStatusException(Map<String, Object> details) {
    super(ErrorCode.ALREADY_EXISTING_USER_STATUS, details);
  }

}
