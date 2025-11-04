package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.user.UserCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.enums.BinaryContentType;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.user.UserAlreadyExistsException;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;
    private final UserMapper userMapper;

    // 유저 생성
    @Transactional
    public UserResponseDto create(UserCreateRequestDto dto,
                                  Optional<BinaryContentCreateRequestDto> profileImageCreateDto) {

        if (userRepository.findByUsername(dto.username()).isPresent()){
            throw UserAlreadyExistsException.byUsername(dto.username());
        }

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new UserAlreadyExistsException(dto.email());
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
                            // .data(bytes)
                            .size((long) bytes.length)
                            .build();

                    binaryContentRepository.save(binaryContent);
                    user.setProfileImage(binaryContent);
                });

        userRepository.save(user);
        userStatusRepository.save(userStatus);
        log.info("회원가입이 완료되었습니다. id=" + user.getId());
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                // .profile(binaryContentMapper.toDto(user.getProfile()))
                .online(true)
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

//        boolean isUserOnline = isUserOnline(user.getId());
        BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfileImage());

        return userMapper.toDto(user, user.getUserStatus(), profileImage);
    }

//    @Transactional(readOnly = true)
//    public UserResponseDto findByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(email));
//
//        boolean isUserOnline = isUserOnline(user.getId());
//        BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfileImage());
//
//        return userMapper.toDto(user, user.getUserStatus(), profileImage);
//    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

//        boolean isUserOnline = isUserOnline(user.getId());
        BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfileImage());

        return userMapper.toDto(user, user.getUserStatus(), profileImage);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll(){
        List<User> users = userRepository.findAllWithStatusAndProfile(); // N+1 문제 해결 위해 fetch join 쿼리 사용

        return users.stream()
                .map(user -> {
//                    boolean isUserOnline = isUserOnline(user.getId());
                    BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfileImage());

                    return userMapper.toDto(user, user.getUserStatus(), profileImage);
                })
                .toList();
    }

    // 수정
    @Transactional
    public UserResponseDto update(UUID id, UserUpdateRequestDto dto,
                                  Optional<BinaryContentCreateRequestDto> optionalProfileCreateRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (dto.newUsername() != null){
            userRepository.findByUsername(dto.newUsername())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw UserAlreadyExistsException.byUsername(dto.newUsername());
                    });
            user.setUsername(dto.newUsername());
        }

        if (dto.newEmail() != null) {
            userRepository.findByEmail(dto.newEmail())
                    .filter(existingUser -> !existingUser.getEmail().equals(user.getEmail()))
                    .ifPresent(existingUser -> {
                        throw new UserAlreadyExistsException(dto.newEmail());
                    });
            user.setEmail(dto.newEmail());
        }

        if (dto.newPassword() != null) {
            user.setPassword(dto.newPassword());
        }

        optionalProfileCreateRequest
                .ifPresent(profileRequest -> {
                    BinaryContent binaryContent = BinaryContent.builder()
                            .user(user)
                            .fileName(profileRequest.fileName())
                            .extension(profileRequest.extension())
                            .type(BinaryContentType.PROFILE_IMAGE)
                            // .data(profileRequest.bytes())
                            .size((long) profileRequest.bytes().length)
                            .build();
                    binaryContentRepository.save(binaryContent);
                    user.setProfileImage(binaryContent);
                });

        userRepository.save(user); // 명시적 저장
        log.info("사용자 수정이 완료되었습니다. id=" + user.getId());

        BinaryContentResponseDto profileImage = binaryContentMapper.toDto(user.getProfileImage());
        return userMapper.toDto(user, user.getUserStatus(), profileImage);
    }

    // 유저 삭제
    @Transactional
    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
        log.info("사용자 삭제가 완료되었습니다. id=" + id);
    }

//    /**
//     * 특정 유저의 온라인 상태를 확인합니다.
//     * UserStatus가 존재하지 않으면 false를 반환합니다.
//     *
//     * @param userId 확인할 유저의 UUID
//     * @return 유저가 온라인 상태인지 여부
//     */
//    private boolean isUserOnline(UUID userId) {
//        return userStatusRepository.findByUserId(userId)
//                .map(UserStatus::isOnline)
//                .orElseGet(() -> {
//                    log.warn("해당 유저에 대해 UserStatus가 존재하지 않습니다: " +  userId);
//                    return false;
//                });
//    }
}