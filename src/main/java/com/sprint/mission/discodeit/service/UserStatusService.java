package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.UserStatus.CreateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UpdateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatusDto create(CreateUserStatusDto createUserStatusDTO);
    List<UserStatusDto> findAll();
    void update(UpdateUserStatusDto updateUserStatusDTO);
    UserStatusDto updateByUserId(UUID userId,UserStatusUpdateRequest updateUserStatusUpdateRequest);
    void delete(UUID id);

}
