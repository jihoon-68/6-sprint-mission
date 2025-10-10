package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.LoginRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Transactional
    public UserResponseDto login(LoginRequestDto request){

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        if (!user.getPassword().equals(request.password())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    // UserStatus가 없다면 새로 생성하여 저장합니다.
                    log.warn("해당 유저에 대해 UserStatus가 존재하지 않습니다: " + user.getUsername());
                    UserStatus newUserStatus = userStatusRepository.save(UserStatus.builder()
                            .user(user)
                            .lastActiveAt(Instant.now())
                            .build());
                    log.warn("UserStatus를 새로 생성했습니다: " + newUserStatus.getId());
                    return newUserStatus;
                });

        userStatus.setLastActiveAt(Instant.now());

        log.info("로그인 되었습니다: " +  user.getId());
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileImage(BinaryContentMapper.toDto(user.getProfileImage()))
                .online(true)
                .build();
    }
}
