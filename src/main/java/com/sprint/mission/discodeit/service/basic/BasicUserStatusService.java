package com.sprint.mission.discodeit.service.basic;


import com.sprint.mission.discodeit.dto.userstatus.CreateUserStatus;
import com.sprint.mission.discodeit.dto.userstatus.UpdateUserStatusRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;

  @Override
  public UserStatus create(CreateUserStatus createUserStatus) {
    if (userStatusRepository.existsById(createUserStatus.userId())) {
      throw new IllegalArgumentException("이미 유저상태 객체가 있습니다");
    }
    User user = userRepository.findById(createUserStatus.userId())
        .orElseThrow(() -> new NotFoundException(
            "유저가 없습니다: " + createUserStatus.userId()));
    return userStatusRepository.save(UserStatus.fromUser(user, Instant.now()));
  }

  @Override
  @Transactional(readOnly = true)
  public UserStatus find(UUID userStatusId) {
    return userStatusRepository.findById(userStatusId)
        .orElseThrow(
            () -> new NotFoundException("UserStatus with id " + userStatusId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserStatus> findAll() {
    return userStatusRepository.findAll();
  }

  @Override
  public UserStatus update(UUID userId, UpdateUserStatusRequest updateUserStatusRequest) {
    UserStatus userStatus = userStatusRepository.findByUser_Id(userId)
        .orElseThrow(() -> new NotFoundException(
            "UserStatus with userId " + userId + " not found"));
    userStatus.update(updateUserStatusRequest.newLastActiveAt());
    return userStatusRepository.save(userStatus);
  }

  @Override
  public UserStatus updateByUserId(UUID userId) {
    UserStatus userStatus = userStatusRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("해당 유저 없음"));
    userStatus.update(Instant.now());
    return userStatusRepository.save(userStatus);
  }

  @Override
  public void delete(UUID userStatusId) {
    if (!userStatusRepository.existsById(userStatusId)) {
      throw new NotFoundException("UserStatus with id " + userStatusId + " not found");
    }
    userStatusRepository.deleteById(userStatusId);
  }
}
