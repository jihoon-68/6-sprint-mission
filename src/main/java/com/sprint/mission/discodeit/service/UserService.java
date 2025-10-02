package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.CreateUserRequest;
import com.sprint.mission.discodeit.dto.user.UpdateUserRequest;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  User create(CreateUserRequest createUserRequest, Optional<MultipartFile> profile);

  User find(UUID userId);

  List<User> findAll();

  User update(UUID userId, UpdateUserRequest updateUserRequest, Optional<MultipartFile> profile);

  User updateState(UUID userId, boolean online);

  void delete(UUID userId);
}
