package com.sprint.mission.discodeit.dto.User;


import java.util.UUID;

public record CreateUserDto(
        String userName,
        Integer age,
        String email,
        UUID profileId,
        String password
){}
