package com.sprint.mission.discodeit.exception.message;

import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.MessageException;
import java.util.Map;

public class NoSuchMessageException extends MessageException {

  public NoSuchMessageException() {
    super(ErrorCode.NO_SUCH_MESSAGE, Map.of());
  }

  public NoSuchMessageException(Map<String, Object> details) {
    super(ErrorCode.NO_SUCH_MESSAGE, details);
  }

}
