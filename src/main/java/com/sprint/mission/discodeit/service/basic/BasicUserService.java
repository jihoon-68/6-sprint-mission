package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.user.UserDto;
import com.sprint.mission.discodeit.dto.message.BinaryContentDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public User create(UserCreateRequest userCreateRequest,
      Optional<BinaryContentDto> profileCreateRequest) {

    if (userRepository.existsByEmail(userCreateRequest.email())) {
      throw new IllegalArgumentException(
          "User with email " + userCreateRequest.email() + " already exists");
    }
    if (userRepository.existsByUsername(userCreateRequest.username())) {
      throw new IllegalArgumentException(
          "User with username " + userCreateRequest.username() + " already exists");
    }

    BinaryContent profile = profileCreateRequest
        .map(req -> new BinaryContent(req.filename(), req.size(), req.contentType(), req.bytes()))
        .orElse(null);

    String plainPassword = userCreateRequest.password();

    User user = new User(
        userCreateRequest.username(),
        userCreateRequest.email(),
        plainPassword,
        profile
    );
    UserStatus userStatus = new UserStatus(
        user,
        Instant.now(),
        Boolean.TRUE
    );
    user.setUserStatus(userStatus);
    User savedUser = userRepository.save(user);
    return savedUser;
  }

  @Override
  public UserDto find(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    return userMapper.toDto(user);
  }

  @Override
  public List<UserDto> findAll() {
    return userRepository.findAll().stream()
        .map(userMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public User update(UUID userId, UserUpdateRequest userUpdateRequest,
      Optional<BinaryContentDto> profileCreateRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

    String newUsername = userUpdateRequest.newUsername();
    String newEmail = userUpdateRequest.newEmail();
    String newPassword = userUpdateRequest.newPassword();

    if (newEmail != null && !user.getEmail().equals(newEmail) && userRepository.existsByEmail(
        newEmail)) {
      throw new IllegalArgumentException("User with email " + newEmail + " already exists");
    }
    if (newUsername != null && !user.getUsername().equals(newUsername)
        && userRepository.existsByUsername(newUsername)) {
      throw new IllegalArgumentException("User with username " + newUsername + " already exists");
    }

    String plainNewPassword = newPassword;

    BinaryContent newProfile = profileCreateRequest
        .map(req -> new BinaryContent(req.filename(), req.size(), req.contentType(), req.bytes()))
        .orElse(null);

    user.update(
        newUsername,
        newEmail,
        plainNewPassword,
        newProfile
    );
    return user;
  }

  @Override
  @Transactional
  public void delete(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    userRepository.delete(user);
  }
}