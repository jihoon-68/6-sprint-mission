package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.Auth.LoginDTO;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    public User login(LoginDTO loginDTO) throws AuthenticationException {
        User user = userRepository.findByEmail(loginDTO.username())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if(!user.getPassword().equals(loginDTO.password())) {
            throw new AuthenticationException("Wrong password");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        //로그인시 오프라인 -> 온라인으로
        userStatus.connect();
        userStatusRepository.save(userStatus);
        return user;
    }
}
