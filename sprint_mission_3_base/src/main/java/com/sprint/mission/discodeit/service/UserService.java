package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.User.UserDto;
import com.sprint.mission.discodeit.DTO.User.UserResponse;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(UserResponse request);
    UserDto find(UUID userId);
    List<UserDto> findAll();
    User update(UserResponse request);
    void delete(UUID userId);
}
