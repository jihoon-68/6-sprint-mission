package com.sprint.mission.discodeit.dto.api.response;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadStatusResponseDTO {

  @Builder
  public record FindReadStatusResponse(
      UUID id,
      UUID userId,
      UUID channelId,
      Instant lastReadAt
  ) {

  }
}
