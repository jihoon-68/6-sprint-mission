package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusDto;

import java.util.Set;
import java.util.UUID;

public interface UserStatusService {

    UserStatusDto.Response create(UserStatusDto.Request request);

    UserStatusDto.Response read(UUID id);

    Set<UserStatusDto.Response> readAll();

    UserStatusDto.Response update(UUID id, UserStatusDto.Request request);

    void delete(UUID id);
}
