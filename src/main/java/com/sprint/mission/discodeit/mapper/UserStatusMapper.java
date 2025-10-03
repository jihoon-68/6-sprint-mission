package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponseDto;
import com.sprint.mission.discodeit.entity.UserStatus;

public class UserStatusMapper {

    public static UserStatusResponseDto toDto(UserStatus userStatus) {
        return UserStatusResponseDto.builder()
                .id(userStatus.getId())
                .userId(userStatus.getUserId())
                .build();
    }
}
