package com.sprint.mission.service;

import com.sprint.mission.dto.user.UserCreateDto;
import com.sprint.mission.dto.user.UserReturnDto;
import com.sprint.mission.dto.user.UserUpdateDto;
import com.sprint.mission.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User create(UserCreateDto userCreateDto);
    UserReturnDto find(UUID id);
    List<UserReturnDto> findAll();
    void update(UserUpdateDto userUpdateDto);
    void delete(UUID id);

}
