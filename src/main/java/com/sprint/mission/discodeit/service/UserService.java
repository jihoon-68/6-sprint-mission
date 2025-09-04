package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.User.UserRequest;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(UserRequest userRequest);
    UserDto find(UUID userId);
    List<UserDto> findAll();
    User update(UserRequest userRequest);
    void delete(UUID userId);
}
