package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.BinaryContentType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    // 유저 생성
    @Transactional
    public UserResponseDto create(UserCreateRequestDto dto,
                                  Optional<BinaryContentCreateRequestDto> profileImageCreateDto) {

        if (userRepository.findByUsername(dto.username()).isPresent()){
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        User user = User.builder()
                .email(dto.email())
                .username(dto.username())
                .password(dto.password())
                .build();

        UserStatus userStatus = UserStatus.builder()
                .user(user)
                .lastActiveAt(Instant.now())
                .build();

        user.setUserStatus(userStatus);

        profileImageCreateDto
                .ifPresent(profileRequest -> {
                    byte[] bytes = profileRequest.bytes();

                    BinaryContent binaryContent = BinaryContent.builder()
                            .user(user)
                            .fileName(profileRequest.fileName())
                            .extension(profileRequest.extension())
                            .type(BinaryContentType.PROFILE_IMAGE)
                            .data(bytes)
                            .size((long) bytes.length)
                            .build();

                    binaryContentRepository.save(binaryContent);
                    user.setProfileImage(binaryContent);
                });

        userRepository.save(user);
        userStatusRepository.save(userStatus);
        log.info("유저 추가 완료: " + user.getUsername());
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(true)
                .build();
    }

    public UserResponseDto findByUsername(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        boolean isUserOnline = checkUserOnlineStatus(user.getId());

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(isUserOnline)
                .build();
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        boolean isUserOnline = checkUserOnlineStatus(user.getId());

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(isUserOnline)
                .build();
    }

    public UserResponseDto findById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        boolean isUserOnline = checkUserOnlineStatus(user.getId());

        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(isUserOnline)
                .build();
    }

    public List<UserResponseDto> findAll(){
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    boolean isUserOnline = checkUserOnlineStatus(user.getId());

                    return UserResponseDto.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .username(user.getUsername())
                            .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                            .online(isUserOnline)
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 수정
    @Transactional
    public UserResponseDto update(UUID id, UserUpdateRequestDto dto,
        Optional<BinaryContentCreateRequestDto> optionalProfileCreateRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        if (dto.newUsername() != null){
            userRepository.findByUsername(dto.newUsername())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw new IllegalStateException("이미 사용중인 닉네임입니다.");
                    });
            user.setUsername(dto.newUsername());
        }

        if (dto.newEmail() != null) {
            userRepository.findByEmail(dto.newEmail())
                    .filter(existingUser -> !existingUser.getEmail().equals(user.getEmail()))
                    .ifPresent(existingUser -> {
                        throw new IllegalStateException("이미 사용중인 이메일입니다.");
                    });
            user.setEmail(dto.newEmail());
        }

        if (dto.newPassword() != null) {
            user.setPassword(dto.newPassword());
        }

        optionalProfileCreateRequest
                .ifPresent(profileRequest -> {
                    byte[] bytes = profileRequest.bytes();

                    BinaryContent binaryContent = BinaryContent.builder()
                            .user(user)
                            .fileName(profileRequest.fileName())
                            .extension(profileRequest.extension())
                            .type(BinaryContentType.PROFILE_IMAGE)
                            .data(bytes)
                            .size((long) bytes.length)
                            .build();

                    binaryContentRepository.save(binaryContent);
                    user.setProfileImage(binaryContent);
                });

        userRepository.save(user);

        boolean isUserOnline = userStatusRepository.findByUserId(id)
                .map(UserStatus::isOnline)
                .orElse(false);

        try {
            isUserOnline = userStatusRepository.findByUserId(user.getId())
                    .map(UserStatus::isOnline)
                    .orElse(false);
        } catch (Exception e) {
            log.warn("해당 유저에 대해 UserStatus가 존재하지 않습니다.");
        }

        log.info("수정 및 저장 완료 : " + user.getUsername());
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(isUserOnline)
                .build();
    }

    // 유저 삭제
    public void delete(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        userStatusRepository.findByUserId(id)
                .ifPresentOrElse(
                        userStatus -> userStatusRepository.deleteById(userStatus.getId()), // userStatus -> userStatusRepository.delete(userStatus)와 동일
                        () -> log.info("해당 유저에 대해 UserStatus가 없습니다.")
                );

        userRepository.delete(user);
        log.info("유저 삭제 완료: " + id);
    }

    // 유저 모두 삭제
    public void clear(){
        userRepository.clear();
    }

    /**
     * 특정 유저의 온라인 상태를 확인합니다.
     * UserStatus가 존재하지 않으면 false를 반환합니다.
     *
     * @param userId 확인할 유저의 UUID
     * @return 유저가 온라인 상태인지 여부
     */
    private boolean checkUserOnlineStatus(UUID userId) {
        return userStatusRepository.findByUserId(userId)
                .map(UserStatus::isOnline)
                .orElseGet(() -> {
                    log.warn("해당 유저에 대해 UserStatus가 존재하지 않습니다: " +  userId);
                    return false;
                });
    }
}