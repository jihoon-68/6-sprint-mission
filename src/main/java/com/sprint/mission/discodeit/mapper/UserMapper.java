package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContent.BinaryContentDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    public List<UserDto> toDtoList(List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            BinaryContentDto binaryContentDto = binaryContentMapper.toDto(user.getProfile());

            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    binaryContentDto,
                    user.getStatus().isConnecting(Instant.now())
                    );
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
