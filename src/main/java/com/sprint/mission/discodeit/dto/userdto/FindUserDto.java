package com.sprint.mission.discodeit.dto.userdto;

import java.util.UUID;

public record FindUserDto(
        UUID userId,
        String isOnline
){
    public FindUserDto(UUID userId) {
        this(userId, "Online");
    }
}