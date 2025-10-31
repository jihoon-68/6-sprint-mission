package com.sprint.mission.discodeit.dto.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorApiDTO {

  @Builder
  public record ErrorApiResponse(
      Integer code,
      String message
  ) {

  }

}
