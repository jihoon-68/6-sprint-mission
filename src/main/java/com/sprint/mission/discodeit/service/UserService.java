package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDto;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    UserDto.Response create(UserDto.Request request);

    UserDto.OnlineInfoResponse read(UUID id);

    Set<UserDto.OnlineInfoResponse> readAll();

    UserDto.Response update(UUID id, UserDto.Request request);

    void delete(UUID id);
}
