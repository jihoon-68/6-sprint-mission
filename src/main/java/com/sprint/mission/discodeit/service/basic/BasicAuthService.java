package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.auth.AuthRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

  private final UserRepository userRepository;

  public User login(AuthRequest authRequest) {
    User user = userRepository.findAll().stream()
        .filter(users -> users.getUsername().equals(authRequest.username())).findAny()
        .orElseThrow(() -> new NoSuchElementException("일치하는 유저가 없습니다"));
    if (user.getPassword().equals(authRequest.password())) {
      return user;
    }

    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
  }
}
