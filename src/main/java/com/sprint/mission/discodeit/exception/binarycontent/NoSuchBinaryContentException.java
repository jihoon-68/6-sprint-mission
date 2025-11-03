package com.sprint.mission.discodeit.exception.binarycontent;

import com.sprint.mission.discodeit.exception.BinaryContentException;
import com.sprint.mission.discodeit.exception.ErrorCode;
import java.util.Map;

public class NoSuchBinaryContentException extends BinaryContentException {

  public NoSuchBinaryContentException() {
    super(ErrorCode.NO_SUCH_BINARY_CONTENT, Map.of());
  }

  public NoSuchBinaryContentException(Map<String, Object> details) {
    super(ErrorCode.NO_SUCH_BINARY_CONTENT, details);
  }

}
