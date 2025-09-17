package com.sprint.mission.discodeit.DTO.User;

import java.util.UUID;

public record UserDto(
    UUID id,
    String userName,
    String email,
    boolean isActive
){
}
