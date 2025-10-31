package com.sprint.mission.discodeit.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelRequestDTO {

  @Builder
  public record PublicChannelCreateRequest(
      @NotBlank(message = "채널 이름을 입력하세요.") String name,
      String description) {

  }

  @Builder
  public record PrivateChannelCreateRequest(
      @JsonProperty("participantIds")
      List<UUID> participantIdList
  ) {

  }

  @Builder
  public record ChannelUpdateRequest(
      @JsonProperty("newName")
      @NotBlank(message = "채널 이름을 입력하세요.")
      String name,
      @JsonProperty("newDescription")
      String description) {

  }
}
