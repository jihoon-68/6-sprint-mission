package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDto.CreateUserDto;
import com.sprint.mission.discodeit.dto.UserDto.FindUserDto;
import com.sprint.mission.discodeit.dto.UserDto.UpdateUserDto;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(CreateUserDto createUserDto);
    User find(FindUserDto findUserDto);
    List<User> findAll();
    User update(UpdateUserDto updateUserDto);
    void delete(UUID userId);
}
