package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.AuthDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.AuthMapper;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import org.springframework.stereotype.Service;

import java.time.InstantSource;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.*;

@Service
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final AuthMapper authMapper;
    private final InstantSource instantSource;
    private final UserStatusMapper userStatusMapper;

    public BasicAuthService(
            UserRepository userRepository,
            UserStatusRepository userStatusRepository,
            AuthMapper authMapper,
            InstantSource instantSource,
            UserStatusMapper userStatusMapper) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
        this.authMapper = authMapper;
        this.instantSource = instantSource;
        this.userStatusMapper = userStatusMapper;
    }

    @Override
    public AuthDto.Response login(AuthDto.Request request) {
        User user = userRepository.find(request.nickname()).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND_BY_NICKNAME.formatted(request.nickname())));
        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException(INVALID_PASSWORD_FOR_NICKNAME.formatted(request.nickname()));
        }
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId()).orElseThrow(() ->
                new IllegalStateException(USER_STATUS_NOT_FOUND_BY_USER_ID.formatted(user.getId())));
        userStatus = userStatusMapper.update(instantSource.instant(), userStatus);
        userStatusRepository.save(userStatus);
        return authMapper.toResponse(user);
    }
}
