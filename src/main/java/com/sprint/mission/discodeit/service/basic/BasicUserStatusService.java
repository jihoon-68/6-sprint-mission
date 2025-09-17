package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.USER_NOT_FOUND_BY_USER_ID;
import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.USER_STATUS_NOT_FOUND_BY_ID;

@Service
public class BasicUserStatusService implements UserStatusService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserStatusMapper userStatusMapper;

    public BasicUserStatusService(
            UserRepository userRepository,
            UserStatusRepository userStatusRepository,
            UserStatusMapper userStatusMapper
    ) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
        this.userStatusMapper = userStatusMapper;
    }

    @Override
    public UserStatusDto.Response create(UserStatusDto.Request request) {
        userRepository.find(request.userId()).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND_BY_USER_ID.formatted(request.userId())));
        UserStatus userStatus = userStatusMapper.from(request, Instant.MIN);
        userStatus = userStatusRepository.save(userStatus);
        return userStatusMapper.toResponse(userStatus);
    }

    @Override
    public UserStatusDto.Response read(UUID id) {
        UserStatus userStatus = userStatusRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(USER_STATUS_NOT_FOUND_BY_ID.formatted(id)));
        return userStatusMapper.toResponse(userStatus);
    }

    @Override
    public Set<UserStatusDto.Response> readAll() {
        return userStatusRepository.findAll()
                .stream()
                .map(userStatusMapper::toResponse)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public UserStatusDto.Response update(UUID id, UserStatusDto.Request request) {
        UserStatus userStatus = userStatusRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(USER_STATUS_NOT_FOUND_BY_ID.formatted(id)));
        userStatus = userStatusMapper.update(request.lastActivatedAt(), userStatus);
        userStatus = userStatusRepository.save(userStatus);
        return userStatusMapper.toResponse(userStatus);
    }

    @Override
    public void delete(UUID id) {
        UserStatus userStatus = userStatusRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(USER_STATUS_NOT_FOUND_BY_ID.formatted(id)));
        userStatusRepository.delete(userStatus.getId());
    }
}
