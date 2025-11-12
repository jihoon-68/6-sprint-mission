package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.ErrorCode;
import com.sprint.mission.discodeit.exception.UserAlreadyExistsException;
import com.sprint.mission.discodeit.exception.UserException;
import com.sprint.mission.discodeit.exception.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;     // ✅ 추가
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j // ✅ 추가
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;
  private final UserMapper userMapper;
  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentStorage binaryContentStorage;

  @Transactional
  @Override
  public UserDto create(UserCreateRequest userCreateRequest,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {

    long started = System.currentTimeMillis();
    String username = userCreateRequest.username();
    String email = userCreateRequest.email();

    log.info("User create requested: username='{}', email='{}'", username, email);

    // 중복 체크(비즈니스 경계: INFO), 충돌은 WARN
    if (userRepository.existsByEmail(email)) {
      log.warn("User create rejected: duplicated email='{}'", email);
      throw new UserAlreadyExistsException(email);
    }
    if (userRepository.existsByUsername(username)) {
      log.warn("User create rejected: duplicated username='{}'", username);
      throw new UserAlreadyExistsException(username);
    }

    // 첨부 파일 처리 (DEBUG 상세)
    BinaryContent nullableProfile = optionalProfileCreateRequest
        .map(profileRequest -> {
          String fileName = profileRequest.fileName();
          String contentType = profileRequest.contentType();
          byte[] bytes = profileRequest.bytes();
          log.debug("Profile attachment detected: fileName='{}', contentType='{}', size={}",
              fileName, contentType, bytes.length);

          BinaryContent binaryContent = new BinaryContent(fileName, (long) bytes.length,
              contentType);
          binaryContentRepository.save(binaryContent);
          binaryContentStorage.put(binaryContent.getId(), bytes);
          return binaryContent;
        })
        .orElse(null);

    // 비밀번호는 절대 로그에 원문으로 남기지 않기!
    String password = userCreateRequest.password();

    User user = new User(username, email, password, nullableProfile);
    Instant now = Instant.now();
    UserStatus userStatus = new UserStatus(user, now);

    userRepository.save(user);
    // (선택) userStatus도 저장해야 한다면 여기에 save 추가

    long took = System.currentTimeMillis() - started;
    log.info("User created: userId={}, username='{}', tookMs={}", user.getId(), username, took);

    return userMapper.toDto(user);
  }

  @Override
  public UserDto find(UUID userId) {
    log.debug("User find requested: userId={}", userId);
    return userRepository.findById(userId)
        .map(user -> {
          log.info("User found: userId={}, username='{}'", user.getId(), user.getUsername());
          return userMapper.toDto(user);
        })
        .orElseThrow(() -> {
          log.warn("User not found: userId={}", userId);

          return new UserNotFoundException(userId);
        });
  }

  @Override
  public List<UserDto> findAll() {
    long started = System.currentTimeMillis();
    log.debug("User findAll requested");
    List<UserDto> result = userRepository.findAllWithProfileAndStatus()
        .stream()
        .map(userMapper::toDto)
        .toList();
    log.info("User findAll done: count={}, tookMs={}", result.size(),
        System.currentTimeMillis() - started);
    return result;
  }

  @Transactional
  @Override
  public UserDto update(UUID userId, UserUpdateRequest userUpdateRequest,
      Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {

    long started = System.currentTimeMillis();
    log.info("User update requested: userId={}", userId);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> {
          log.warn("User update rejected: not found userId={}", userId);
          return new UserNotFoundException(userId);
        });

    String newUsername = userUpdateRequest.newUsername();
    String newEmail = userUpdateRequest.newEmail();

    // ⚠️ 현재 구현은 본인 제외 중복체크가 없음: 실서비스라면 existsByEmailAndIdNot(...) 같은 메서드 권장
    if (userRepository.existsByEmail(newEmail)) {
      log.warn("User update rejected: duplicated email='{}', userId={}", newEmail, userId);
      throw new UserAlreadyExistsException(newEmail);
    }
    if (userRepository.existsByUsername(newUsername)) {
      log.warn("User update rejected: duplicated username='{}', userId={}", newUsername, userId);
      throw new UserAlreadyExistsException(newEmail);
    }

    BinaryContent nullableProfile = optionalProfileCreateRequest
        .map(profileRequest -> {
          String fileName = profileRequest.fileName();
          String contentType = profileRequest.contentType();
          byte[] bytes = profileRequest.bytes();
          log.debug("Profile re-upload: userId={}, fileName='{}', contentType='{}', size={}",
              userId, fileName, contentType, bytes.length);

          BinaryContent binaryContent = new BinaryContent(fileName, (long) bytes.length,
              contentType);
          binaryContentRepository.save(binaryContent);
          binaryContentStorage.put(binaryContent.getId(), bytes);
          return binaryContent;
        })
        .orElse(null);

    String newPassword = userUpdateRequest.newPassword(); // 로그에 남기지 않음
    user.update(newUsername, newEmail, newPassword, nullableProfile);

    long took = System.currentTimeMillis() - started;
    log.info("User updated: userId={}, newUsername='{}', tookMs={}", userId, newUsername, took);

    return userMapper.toDto(user);
  }

  @Transactional
  @Override
  public void delete(UUID userId) {
    log.info("User delete requested: userId={}", userId);

    // ✅ 버그 수정: 존재하지 않으면 예외
    if (!userRepository.existsById(userId)) {
      log.warn("User delete rejected: not found userId={}", userId);
      throw new UserNotFoundException(userId);
    }

    userRepository.deleteById(userId);
    log.info("User deleted: userId={}", userId);
  }
}