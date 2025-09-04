package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record UserRequest(
         UUID userId,
         String username,
         String email,
         String password,
         String attatchmentUrl
) {

}

