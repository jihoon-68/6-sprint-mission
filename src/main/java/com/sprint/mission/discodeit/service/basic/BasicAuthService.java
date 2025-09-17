package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.auth.request.LoginRequest;
import com.sprint.mission.discodeit.dto.auth.request.SignupRequest;
import com.sprint.mission.discodeit.dto.user.model.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository  userRepository;
    private final UserStatusRepository  userStatusRepository;

    @Override
    public UserDto login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            log.info("잘못된 email, password");
            throw new IllegalArgumentException("email또는 password를 확인해주세요.");
        }
        UserStatus status = userStatusRepository.findByUserId(user.getId());
        status.setLogin(true);
        userStatusRepository.save(status);

        return user.toDto(status);
    }

    @Override
    public void signup(SignupRequest request) {
        if (request.getEmail().contains(" ")) {
            log.info("잘못된 email 형식");
            throw new IllegalArgumentException("email에 공백을 포함할 수 없습니다.");
        }

        if (request.getPassword().length() < 6) {
            log.info("잘못된 password 형식");
            throw new IllegalArgumentException("password는 6자 이상이여야 합니다.");
        }

        if (userRepository.findByEmail(request.getEmail()) != null) {
            log.info("중복된 email");
            throw new IllegalStateException("이미 사용중인 email입니다.");
        }

        User user = new User(request.getEmail(), request.getUsername(), request.getPassword());

        UserStatus status = new UserStatus(user.getId());

        userRepository.save(user);
        userStatusRepository.save(status);
    }

    @Override
    public void logout(UUID userId) {
        User user = userRepository.findById(userId);
        UserStatus status = userStatusRepository.findByUserId(user.getId());
        status.setLogin(false);
        status.setLastLogin(Instant.now());
        userStatusRepository.save(status);
    }
}
