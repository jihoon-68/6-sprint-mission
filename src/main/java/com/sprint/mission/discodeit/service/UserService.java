package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.User.UserCreateRequest;
import com.sprint.mission.discodeit.DTO.User.FindUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserDTO;
import com.sprint.mission.discodeit.DTO.User.UpdateUserResponse;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
      User create(List<MultipartFile> multipartFile, UserCreateRequest userCreateRequest);
      FindUserDTO find(UUID id);
      FindUserDTO findEmail(String email);
      List<FindUserDTO> findAll();
      UpdateUserResponse update(List<MultipartFile> multipartFile , UpdateUserDTO updateUserDTO);
      void delete(UUID id);
}
