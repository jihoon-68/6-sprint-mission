package com.sprint.mission.discodeit.exception.channel;

import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.util.Map;

public class NoSuchChannelException extends ChannelException {

  public NoSuchChannelException(Map<String, Object> details) {
    super(ErrorCode.NO_SUCH_CHANNEL, details);
  }

}
