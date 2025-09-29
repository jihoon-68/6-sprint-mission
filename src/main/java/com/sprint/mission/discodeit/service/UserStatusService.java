package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.UserStatus.CreateUserStatusDTO;
import com.sprint.mission.discodeit.dto.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(CreateUserStatusDTO createUserStatusDTO);
    UserStatus findById(UUID id);
    List<UserStatus> findAll();
    void update(UpdateUserStatusDTO  updateUserStatusDTO);
    UserStatus updateByUserId(UUID userId,UserStatusUpdateRequest updateUserStatusUpdateRequest);
    void delete(UUID id);

}
