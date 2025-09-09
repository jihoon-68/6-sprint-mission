package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userdto.CreateUserDto;
import com.sprint.mission.discodeit.dto.userdto.FindUserDto;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserDto;
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
