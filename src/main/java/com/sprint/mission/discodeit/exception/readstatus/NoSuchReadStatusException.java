package com.sprint.mission.discodeit.exception.readstatus;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.ReadStatusException;

public class NoSuchReadStatusException extends ReadStatusException {

  public NoSuchReadStatusException() {
    super(ErrorCode.NO_SUCH_READ_STATUS, null);
  }

}
