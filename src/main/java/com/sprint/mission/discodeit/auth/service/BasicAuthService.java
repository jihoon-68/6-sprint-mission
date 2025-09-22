package com.sprint.mission.discodeit.auth.service;

import com.sprint.mission.discodeit.auth.AuthDto.Request;
import com.sprint.mission.discodeit.auth.AuthDto.Response;
import com.sprint.mission.discodeit.auth.AuthMapper;
import com.sprint.mission.discodeit.user.UserDto;
import com.sprint.mission.discodeit.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.InstantSource;

@Service
public class BasicAuthService implements AuthService {

    private final UserService userService;
    private final InstantSource instantSource;

    public BasicAuthService(UserService userService, InstantSource instantSource) {
        this.userService = userService;
        this.instantSource = instantSource;
    }

    @Override
    public Response login(Request request) {
        UserDto.ResponseWithLastActivatedAt userResponse =
                userService.getUserByNicknameAndPassword(request.nickname(), request.password());
        Instant now = instantSource.instant();
        userResponse = userService.updateUserById(userResponse.id(), now);
        return AuthMapper.toResponse(userResponse);
    }
}
