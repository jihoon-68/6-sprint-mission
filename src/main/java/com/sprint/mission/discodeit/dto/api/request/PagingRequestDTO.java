package com.sprint.mission.discodeit.dto.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PagingRequestDTO {

  @Builder
  public record OffsetRequest(
      @NotBlank(message = "올바르지 않은 페이지 형식입니다.") int size,
      @NotBlank(message = "올바르지 않은 페이지 형식입니다.") int page,
      String sort
  ) {}

  @Builder
  public record CursorRequest(
      @NotBlank(message = "올바르지 않은 페이지 형식입니다.") int size,
      String sort
  ) {}
}
