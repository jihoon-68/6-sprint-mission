package com.sprint.mission.discodeit.DTO.User;


import java.util.UUID;

public record CreateUserDTO(
        String userName,
        Integer age,
        String email,
        UUID profileId,
        String password
){}
