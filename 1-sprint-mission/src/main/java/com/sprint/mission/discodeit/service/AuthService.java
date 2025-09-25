package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.DTOs.Auth.AuthInfo;
import com.sprint.mission.discodeit.entity.User;

public interface AuthService {
    User login(AuthInfo info);
}
