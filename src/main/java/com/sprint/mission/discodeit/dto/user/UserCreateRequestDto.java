package com.sprint.mission.discodeit.dto.user;

import java.util.UUID;

public record UserCreateRequestDto(
        String email,
        String username,
        String password,
        UUID profileImageId // 등록하고 싶지 않으면 null로 받음
) {}
