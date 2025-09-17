package com.sprint.mission.discodeit.dto.user.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserUpdateProfileImageRequest {
    private UUID userId;
    private String path;
}
