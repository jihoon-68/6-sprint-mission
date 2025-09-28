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
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public UserResponseDto create(UserCreateRequestDto dto,
                                  Optional<BinaryContentCreateRequestDto> profileImageCreateDto) {
        if (userRepository.findByUsername(dto.username()).isPresent()){
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
        User user = new User(dto.email(), dto.username(), dto.password());
        UserStatus userStatus = new UserStatus(user.getId());

        UUID nullableProfileId = profileImageCreateDto
                .map(profileRequest -> {
                    String fileName = profileRequest.fileName();
                    byte[] bytes = profileRequest.bytes();
                    BinaryContent binaryContent = new BinaryContent(
                            fileName,
                            profileRequest.extension(),
                            BinaryContentType.PROFILE_IMAGE,
                            bytes,
                            (long) bytes.length);
                    binaryContentRepository.save(binaryContent);
                    return binaryContent.getId();
                })
                .orElse(null);

        user.setProfileImageId(nullableProfileId);
        userRepository.save(user);
        userStatusRepository.save(userStatus);
        log.info("유저 추가 완료: " + user.getUsername());
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                // userStatus.isOnline(),
                user.getProfileImageId());
    }

    public UserResponseDto findByUsername(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                // userStatus.isOnline(),
                user.getProfileImageId()
                );
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));

        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                // userStatus.isOnline(),
                user.getProfileImageId()
        );
    }

    public UserResponseDto findById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        UserStatus userStatus = userStatusRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 UserStatus입니다."));

        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                // userStatus.isOnline(),
                user.getProfileImageId()
        );
    }

    public List<UserResponseDto> findAll(){
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    boolean isUserOnline = userStatusRepository.findByUserId(user.getId())
                            .map(UserStatus::isOnline)
                            .orElseGet(() -> {
                                log.warn("해당 유저에 대해 UserStatus가 존재하지 않습니다: " +  user.getUsername());
                                return false;
                            });

                    return new UserResponseDto(
                            user.getId(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.getEmail(),
                            user.getUsername(),
                            // isUserOnline,
                            user.getProfileImageId()
                    );
                })
                .collect(Collectors.toList());
    }

    // 수정
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

        UUID nullableProfileId = optionalProfileCreateRequest
                .map(profileRequest -> {
                    Optional.ofNullable(user.getProfileImageId())
                            .ifPresent(binaryContentRepository::deleteById);

                    String fileName = profileRequest.fileName();
                    BinaryContentType type = profileRequest.type();
                    byte[] bytes = profileRequest.bytes();
                    BinaryContent binaryContent = new BinaryContent(fileName, profileRequest.extension(), type, bytes, (long) bytes.length);
                    binaryContentRepository.save(binaryContent);
                    return binaryContent.getId();
                })
                .orElse(null);

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
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                // isUserOnline,
                user.getProfileImageId()
        );
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
}