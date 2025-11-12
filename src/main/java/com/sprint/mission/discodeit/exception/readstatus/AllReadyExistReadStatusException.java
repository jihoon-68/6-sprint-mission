package com.sprint.mission.discodeit.exception.readstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.ReadStatusException;
import java.util.Map;

public class AllReadyExistReadStatusException extends ReadStatusException {

  public AllReadyExistReadStatusException(Map<String, Object> details) {
    super(ErrorCode.ALREADY_EXISTING_READ_STATUS, details);
  }

}
