package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Auth.LoginDTO;

import com.sprint.mission.discodeit.dto.Auth.LoginRequest;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.Instant;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserMapper userMapper;

    public UserDto login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.username())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if(!user.getPassword().equals(loginRequest.password())) {
            throw new IllegalArgumentException("Wrong password");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        //로그인시 오프라인 -> 온라인으로
        userStatus.update(Instant.now());
        userStatusRepository.save(userStatus);
        return userMapper.toDto(user);
    }
}
