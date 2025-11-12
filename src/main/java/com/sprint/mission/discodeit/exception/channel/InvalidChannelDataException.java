package com.sprint.mission.discodeit.exception.channel;

import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.util.Map;

public class InvalidChannelDataException extends ChannelException {

  public InvalidChannelDataException(Map<String, Object> details) {
    super(ErrorCode.INVALID_CHANNEL_DATA, details);
  }

}
