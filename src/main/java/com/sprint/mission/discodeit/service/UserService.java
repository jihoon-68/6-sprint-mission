package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.User.*;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
      User create(MultipartFile multipartFile, UserCreateRequest userCreateRequest);
      FindUserDTO find(UUID id);
      FindUserDTO findEmail(String email);
      List<FindUserDTO> findAll();
      UpdateUserResponse update(MultipartFile multipartFile , UUID userId, UserUpdateRequest userUpdateRequest);
      void delete(UUID id);
}
