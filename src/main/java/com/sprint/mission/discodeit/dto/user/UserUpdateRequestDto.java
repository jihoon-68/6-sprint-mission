package com.sprint.mission.discodeit.dto.user;

import java.util.UUID;

public record UserUpdateRequestDto (
    UUID userId,
    String email,
    String username,
    String password
    // 바꾸고 싶지 않은 값은 null로 받음.
){}
