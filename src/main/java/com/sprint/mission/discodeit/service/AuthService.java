package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.LoginRequestDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.NotFoundException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserResponseDto login(LoginRequestDto request){

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        /*
        if (user == null) {
            throw new NotFoundException("존재하지 않는 사용자입니다.");
        }
         */

        if (!user.getPassword().equals(request.password())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        UserStatus userStatus = new UserStatus(user.getId());

        log.info("로그인 되었습니다.");
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEmail(),
                user.getUsername(),
                user.getProfileImageId(),
                userStatus.isOnline()
        );
    }
}
