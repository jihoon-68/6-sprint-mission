package com.sprint.mission.discodeit.dto.api;

import java.time.Instant;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorApiDTO {

  @Builder
  public record ErrorApiResponse(
      Instant timestamp,
      String code,
      String message,
      Map<String, Object> details,
      String exceptionType,
      int status
  ) {

  }

}
