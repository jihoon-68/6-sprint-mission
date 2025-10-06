package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.User;

import java.time.Instant;


public class UserMapper {
    BinaryContentMapper binaryContentMapper;

    public UserDto toDto(User user) {
        BinaryContentDto binaryContentDto = binaryContentMapper.toDto(user.getProfile());
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                binaryContentDto,
                user.getStatus().isConnecting(Instant.now())
        );
    }
}
