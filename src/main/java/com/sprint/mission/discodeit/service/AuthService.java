package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Auth.LoginRequest;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    public UserDto login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.username())
                .orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.password())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        //로그인시 오프라인 -> 온라인으로
        user.getStatus().update(Instant.now());
        userStatusRepository.save(user.getStatus());
        return UserMapper.INSTANCE.toDto(user);
    }
}
