package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.User;

public class UserMapper {

    public static UserResponseDto toDto(User user, Boolean online) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(online)
                .build();
    }
}
