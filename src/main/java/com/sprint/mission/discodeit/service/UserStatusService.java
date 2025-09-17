package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.userStatus.model.UserStatusDto;
import com.sprint.mission.discodeit.dto.userStatus.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.userStatus.response.UserStatusFindAllResponse;

import java.util.UUID;

public interface UserStatusService {
    void createUserStatus(UserStatusCreateRequest request);
    UserStatusDto findOneById(UUID id);
    UserStatusFindAllResponse findAll();
}
