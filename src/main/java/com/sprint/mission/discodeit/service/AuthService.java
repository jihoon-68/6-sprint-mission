package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.auth.request.LoginRequest;
import com.sprint.mission.discodeit.dto.auth.request.SignupRequest;
import com.sprint.mission.discodeit.dto.user.model.UserDto;
import com.sprint.mission.discodeit.entity.User;

import java.util.UUID;

public interface AuthService {
    UserDto login(LoginRequest request);
    void signup(SignupRequest request);
    void logout(UUID userId);
}
