package com.sprint.mission.discodeit.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String email;
    private String username;
    private String password;
}
