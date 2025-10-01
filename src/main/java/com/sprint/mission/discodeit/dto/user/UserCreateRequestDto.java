package com.sprint.mission.discodeit.dto.user;

public record UserCreateRequestDto(
        String email,
        String username,
        String password
) {}
