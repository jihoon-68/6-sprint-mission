package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserCreateRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.dto.user.UserUpdateRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    // 유저 생성
    public UserResponseDto create(UserCreateRequestDto dto) {
        if (userRepository.findByUsername(dto.username()) != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        if (userRepository.findByEmail(dto.email()) != null) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
        User user = new User(dto.email(), dto.username(), dto.password(), dto.profileImageId());
        UserStatus userStatus = new UserStatus(user.getId());

        userRepository.save(user);
        System.out.println("유저 추가 완료: " + user.getUsername());
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImageId(),
                userStatus.isOnline());
    }

    public UserResponseDto findByUsername(String name) {
        User user = userRepository.findByUsername(name);
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId());
        boolean isUserOnline = userStatus != null && userStatus.isOnline();
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImageId(),
                isUserOnline);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId());
        boolean isUserOnline = userStatus != null && userStatus.isOnline();
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImageId(),
                isUserOnline);
    }

    public UserResponseDto findById(UUID id){
        User user = userRepository.findById(id);
        UserStatus userStatus = userStatusRepository.findByUserId(id);
        boolean isUserOnline = userStatus != null && userStatus.isOnline();
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImageId(),
                isUserOnline);
    }

    public List<UserResponseDto> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserStatus userStatus = userStatusRepository.findByUserId(user.getId());
                    boolean isUserOnline = (userStatus != null && userStatus.isOnline());
                    return new UserResponseDto(
                            user.getId(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.getEmail(),
                            user.getUsername(),
                            user.getProfileImageId(),
                            isUserOnline);
                })
                .collect(Collectors.toList());
    }

    // 수정
    public UserResponseDto update(UUID id, UserUpdateRequestDto dto) {

        User user = userRepository.findById(id);

        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 유저입니다.");
        }

        if (dto.username() != null){
            User existingUser = userRepository.findByUsername(dto.username());
            if (existingUser != null && !existingUser.getId().equals(user.getId())) {
                throw new IllegalStateException("이미 사용중인 닉네임입니다.");
            }
            user.setUsername(dto.username());
        }

        if (dto.email() != null) {
            User existingUser = userRepository.findByEmail(dto.email());
            if (existingUser != null && !existingUser.getId().equals(user.getId())) {
                throw new IllegalStateException("이미 사용중인 닉네임입니다.");
            }
            user.setEmail(dto.email());
        }

        if (dto.password() != null) user.setPassword(dto.password());

        userRepository.save(user);
        UserStatus userStatus = userStatusRepository.findByUserId(id);
        boolean isUserOnline = (userStatus != null && userStatus.isOnline());

        System.out.println("수정 및 저장 완료 : " + user.getUsername());
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImageId(),
                isUserOnline);
    }

    // 유저 삭제
    public void delete(UUID id) {

        User user = userRepository.findById(id);
        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 유저입니다.");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(id);
        if (userStatus != null) {
            userStatusRepository.delete(userStatus);
        }
        userRepository.delete(user);
        System.out.println("유저 삭제 완료: " + id);
    }

    // 유저 모두 삭제
    public void clear(){
        userRepository.clear();
    }
}