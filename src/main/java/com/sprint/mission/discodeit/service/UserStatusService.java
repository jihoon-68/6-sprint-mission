package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.CreateUserStatusDTO;
import com.sprint.mission.discodeit.DTO.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(CreateUserStatusDTO createUserStatusDTO);
    UserStatus findById(UUID id);
    List<UserStatus> findAll();
    void update(UpdateUserStatusDTO  updateUserStatusDTO);
    void updateByUserId(UpdateUserDTO  updateUserDTO);
    void delete(UUID id);

}
