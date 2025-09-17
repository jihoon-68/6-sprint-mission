package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.LoginRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponseDto login(LoginRequestDto request){

        User user = userRepository.findByUsername(request.username());

        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 사용자입니다.");
        }

        if (!user.getPassword().equals(request.password())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        UserStatus userStatus = new UserStatus(user.getId());

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                userStatus.isOnline()
        );
    }
}
