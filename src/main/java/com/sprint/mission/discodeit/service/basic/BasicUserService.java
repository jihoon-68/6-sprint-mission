package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.UserDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
  public UserDto create(UserCreateRequest userCreateRequest,
      Optional<MultipartFile> profile) {

    if (userRepository.existsByEmail(userCreateRequest.email())) {
      throw new IllegalArgumentException(
          "User with email " + userCreateRequest.email() + " already exists");
    }
    if (userRepository.existsByUsername(userCreateRequest.username())) {
      throw new IllegalArgumentException(
          "User with username " + userCreateRequest.username() + " already exists");
    }

      BinaryContent profileAttachment = profile
              .filter(p -> !p.isEmpty())
              .map(p -> {
                  try {
                      BinaryContentDto dto = BinaryContentDto.from(p, UUID.randomUUID());
                      return new BinaryContent(
                              dto.filename(),
                              dto.size(),
                              dto.contentType()
                      );
                  } catch (IOException e) {
                      throw new RuntimeException("Failed to read profile file data", e);
                  }
              })
              .orElse(null);

      String plainPassword = userCreateRequest.password();
      User user = new User(
              userCreateRequest.username(),
              userCreateRequest.email(),
              plainPassword,
              profileAttachment
      );

      UserStatus userStatus = new UserStatus(
              user,
              Instant.now()
      );
      user.setUserStatus(userStatus);
      User savedUser = userRepository.save(user);
      return userMapper.toDto(savedUser);
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
  public UserDto update(UUID userId, UserUpdateRequest userUpdateRequest,
      Optional<MultipartFile> profile) {
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
      BinaryContent newProfile = profile
              .filter(p -> !p.isEmpty())
              .map(p -> {
                  try {
                      BinaryContentDto dto = BinaryContentDto.from(p, UUID.randomUUID());
                      return new BinaryContent(
                              dto.filename(),
                              dto.size(),
                              dto.contentType()
                      );
                  } catch (IOException e) {
                      throw new RuntimeException("Failed to read profile file data", e);
                  }
              })
              .orElse(null);

      user.update(
              newUsername,
              newEmail,
              plainNewPassword,
              newProfile
      );
      return userMapper.toDto(user);
  }

  @Override
  @Transactional
  public void delete(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    userRepository.delete(user);
  }
}