package com.sprint.mission.discodeit.dto.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRequestDTO {

  public record LoginRequest(
      @NotBlank(message = "계정명을 입력하세요.") String username,
      @NotBlank(message = "비밀번호를 입력하세요.") String password) {

  }
}
