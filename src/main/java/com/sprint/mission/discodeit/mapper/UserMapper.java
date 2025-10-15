package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final BinaryContentMapper binaryContentMapper;

  public UserDto toDto(User entity) {
    if (entity == null) {
      return null;
    }

    return new UserDto(
        entity.getId(),
        entity.getUsername(),
        entity.getEmail(),
        // profile 필드는 BinaryContentMapper를 사용하여 변환
        binaryContentMapper.toDto(entity.getProfile()),
        entity.isOnline() // isOnline() Getter를 사용한다고 가정
    );
  }
}