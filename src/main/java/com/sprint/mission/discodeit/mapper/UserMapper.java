package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class UserMapper {
    private final BinaryContentMapper binaryContentMapper;

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
