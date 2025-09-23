package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.userdto.CreateUserRequest;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;
  private final BinaryContentRepository binaryContentRepository;

  @Override
  public User create(CreateUserRequest createUserRequest, Optional<MultipartFile> profile) {
    User user;
    User findUserByName = userRepository.findByUsername(createUserRequest.username()).orElse(null);
    User findUserByEmail = userRepository.findAll().stream()
        .filter(users -> users.getEmail().equals(createUserRequest.email())).findAny().orElse(null);
    if (findUserByName != null || findUserByEmail != null) {
      throw new IllegalArgumentException("유저네임 혹은 이메일이 같은 유저가 존재합니다.");
    }

    Optional<BinaryContent> binaryContent = profile.map(
        file -> {
          try {
            BinaryContent bc = new BinaryContent(file.getOriginalFilename(), file.getSize(),
                file.getContentType(),
                file.getBytes());
            return binaryContentRepository.save(bc);
          } catch (IOException e) {
            log.error("유저 프로필 사진 처리 실패", e);
            throw new RuntimeException("유저 프로필 사진 처리 실패");
          }
        }
    );
    UUID profileId = binaryContent.map(BinaryContent::getId).orElse(null);
    user = createUserRequest.toEntity(profileId);

    userStatusRepository.save(UserStatus.fromUser(user.getId(), Instant.now()));
    return userRepository.save(user);
  }

  @Override
  public User find(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User update(UUID userId, UpdateUserRequest updateUserRequest,
      Optional<MultipartFile> profile) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    user.update(updateUserRequest.newUsername(), updateUserRequest.newEmail(),
        updateUserRequest.newPassword());
    user.update(updateUserRequest.online());

    Optional<BinaryContent> binaryContent = profile.map(
        file -> {
          try {
            BinaryContent bc = binaryContentRepository.findById(user.getProfileId())
                .orElse(null);
            if (bc == null) {
              bc = new BinaryContent(file.getOriginalFilename(), file.getSize(),
                  file.getContentType(),
                  file.getBytes());
            } else {
              bc.update(file.getOriginalFilename(), file.getSize(), file.getContentType(),
                  file.getBytes());
            }
            return binaryContentRepository.save(bc);
          } catch (IOException e) {
            throw new RuntimeException("이미지 가져오는데 실패");
          }
        }
    );
    UUID profileId = binaryContent.map(BinaryContent::getId).orElse(null);
    user.update(profileId);
    return userRepository.save(user);
  }

  @Override
  public User updateState(UUID userId, boolean online) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    user.update(online);
    return userRepository.save(user);
  }

  @Override
  public void delete(UUID userId) {
    if (!userRepository.existsById(userId)) {
      throw new NoSuchElementException("User with id " + userId + " not found");
    }
    // 유저의 프로필 사진들 객체 삭제
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("UserService delete: userId 없음"));
    List<BinaryContent> binaryContentsInUser = binaryContentRepository.findAll().stream()
        .filter(binaryContent -> binaryContent.getId().equals(user.getProfileId())).toList();
    for (BinaryContent binaryContent : binaryContentsInUser) {
      binaryContentRepository.deleteById(binaryContent.getId());
    }
    // 유저 상태들 삭제
    List<UserStatus> userStatusesInUser = userStatusRepository.findAll().stream()
        .filter(userStatus -> userStatus.getUserId().equals(userId)).toList();
    for (UserStatus userStatus : userStatusesInUser) {
      userStatusRepository.deleteById(userStatus.getId());
    }
    // 유저 id로 삭제
    userRepository.deleteById(userId);
  }
}
