package com.sprint.mission.discodeit.exception.channel;

import com.sprint.mission.discodeit.exception.ChannelException;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.util.Map;

public class AllReadyExistChannelException extends ChannelException {

  public AllReadyExistChannelException(Map<String, Object> details) {
    super(ErrorCode.ALREADY_EXISTING_CHANNEL, details);
  }

}
