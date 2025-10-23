package com.sprint.mission.discodeit.dto.request;

import java.util.UUID;
import lombok.Getter;

@Getter
public class LoginResponse {
  private UUID id;
  private String username;
  private String email;

  public LoginResponse(UUID id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }
}