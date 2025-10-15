package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserStatus create(UserStatusCreateRequest request) {
    UUID userId = request.userId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));
    if (userStatusRepository.findByUserId(userId).isPresent()) {
      throw new IllegalArgumentException("UserStatus for User id " + userId + " already exists");
    }
    Instant lastActiveAt = Instant.now();
    Boolean status = request.status();

    UserStatus userStatus = new UserStatus(user, lastActiveAt, status);
    return userStatusRepository.save(userStatus);
  }

  @Override
  public UserStatus find(UUID userStatusId) {
    return userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new EntityNotFoundException("UserStatus with id " + userStatusId + " not found"));
  }

  @Override
  public List<UserStatus> findAll() {
    return userStatusRepository.findAll();
  }

  @Override
  @Transactional
  public UserStatus update(UUID userStatusId, UserStatusUpdateRequest request) {
    Instant newLastActiveAt = Instant.now();
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new EntityNotFoundException("UserStatus with id " + userStatusId + " not found"));
    userStatus.update(newLastActiveAt);
    return userStatus;
  }

  @Override
  @Transactional
  public UserStatus updateByUserId(UUID userId, UserStatusUpdateRequest request) {
    Instant newLastActiveAt = Instant.now();
    UserStatus userStatus = userStatusRepository.findByUserId(userId)
        .orElseThrow(
            () -> new EntityNotFoundException("UserStatus with userId " + userId + " not found"));
    userStatus.update(newLastActiveAt);
    return userStatus;
  }

  @Override
  @Transactional
  public void delete(UUID userStatusId) {
    UserStatus userStatus = userStatusRepository.findById(userStatusId)
        .orElseThrow(() -> new EntityNotFoundException("UserStatus with id " + userStatusId + " not found"));
    userStatusRepository.delete(userStatus);
  }
}
