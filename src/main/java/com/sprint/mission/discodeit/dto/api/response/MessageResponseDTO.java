package com.sprint.mission.discodeit.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.dto.api.response.BinaryContentResponseDTO.ReadBinaryContentResponse;
import com.sprint.mission.discodeit.dto.api.response.UserResponseDTO.FindUserResponse;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResponseDTO {

  @Builder
  public record FindMessageResponse(
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      String content,
      UUID channelId,
      FindUserResponse author,
      @JsonProperty("attachments")
      List<ReadBinaryContentResponse> attachments
  ) {

  }
}
