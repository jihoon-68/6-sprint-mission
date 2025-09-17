package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTO.Login.LoginRequest;
import com.sprint.mission.discodeit.entity.User;

public interface AuthService {
    User login(LoginRequest request);
}
