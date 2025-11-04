package com.sprint.mission.discodeit.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDTO {

  @Builder
  public record MessageCreateRequest(
      @NotBlank(message = "메시지는 공백을 허용하지 않습니다.") String content,
      @NotNull UUID authorId,
      @NotNull UUID channelId) {

  }

  @Builder
  public record MessageUpdateRequest(
      @NotNull UUID id,
      @JsonProperty("newContent")
      @NotBlank(message = "메시지는 공백을 허용하지 않습니다.") String content) {

  }
}
