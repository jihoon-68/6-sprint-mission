package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.User.CreateUserDTO;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
      User create(CreateUserDTO createUserDTO);
      FindUserDTO find(UUID id);
      List<FindUserDTO> findAll();
      void update(UpdateUserDTO updateUserDTO);
      void delete(UUID id);
}
