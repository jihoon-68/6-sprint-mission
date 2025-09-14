package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.LoginRequest;
import com.sprint.mission.discodeit.dto.UserResponse;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + request.username()));

        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("Invalid password");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseGet(() -> new UserStatus(user.getId()));
        
        userStatus.update();
        userStatusRepository.save(userStatus);

        return UserResponse.of(user, userStatus.isOnline());
    }
}
