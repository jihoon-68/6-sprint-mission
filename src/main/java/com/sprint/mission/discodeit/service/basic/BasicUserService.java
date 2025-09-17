package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.InstantSource;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.USER_NOT_FOUND_BY_ID;
import static com.sprint.mission.discodeit.service.basic.BasicServiceMessageConstants.USER_STATUS_NOT_FOUND_BY_USER_ID;

@Service
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserMapper userMapper;
    private final BinaryContentMapper binaryContentMapper;
    private final UserStatusMapper userStatusMapper;
    private final InstantSource instantSource;

    public BasicUserService(
            BinaryContentRepository binaryContentRepository,
            UserRepository userRepository,
            UserStatusRepository userStatusRepository,
            UserMapper userMapper,
            BinaryContentMapper binaryContentMapper,
            UserStatusMapper userStatusMapper,
            InstantSource instantSource
    ) {
        this.binaryContentRepository = binaryContentRepository;
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
        this.userMapper = userMapper;
        this.binaryContentMapper = binaryContentMapper;
        this.userStatusMapper = userStatusMapper;
        this.instantSource = instantSource;
    }

    @Override
    public UserDto.Response create(UserDto.Request request) {
        User user = userMapper.from(request);
        user = userRepository.save(user);
        if (request.profileImage().length > 0) {
            BinaryContent userProfile = binaryContentMapper.ofUserProfile(user.getId(), request.profileImage());
            binaryContentRepository.save(userProfile);
        }
        UserStatus userStatus = userStatusMapper.of(user.getId(), Instant.MIN);
        userStatusRepository.save(userStatus);
        return userMapper.toResponse(user);
    }

    @Override
    public UserDto.OnlineInfoResponse read(UUID id) {
        User user = userRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND_BY_ID.formatted(id)));
        byte[] profileImage = binaryContentRepository.findUserProfile(user.getId())
                .map(BinaryContent::getData)
                .orElseGet(() -> new byte[0]);
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId()).orElseThrow(() ->
                new IllegalStateException(USER_STATUS_NOT_FOUND_BY_USER_ID.formatted(user.getId())));
        Instant now = instantSource.instant();
        return userMapper.toResponse(user, profileImage, userStatus.isActive(now));
    }

    @Override
    public Set<UserDto.OnlineInfoResponse> readAll() {
        Instant now = instantSource.instant();
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    byte[] profileImage = binaryContentRepository.findUserProfile(user.getId())
                            .map(BinaryContent::getData)
                            .orElseGet(() -> new byte[0]);
                    UserStatus userStatus = userStatusRepository.findByUserId(user.getId()).orElseThrow(() ->
                            new IllegalStateException(USER_STATUS_NOT_FOUND_BY_USER_ID.formatted(user.getId())));
                    return userMapper.toResponse(user, profileImage, userStatus.isActive(now));
                })
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public UserDto.Response update(UUID id, UserDto.Request request) {
        User user = userRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND_BY_ID.formatted(id)));
        binaryContentRepository.findUserProfile(user.getId())
                .ifPresent(userProfile -> binaryContentRepository.delete(userProfile.getId()));
        if (request.profileImage().length > 0) {
            BinaryContent userProfile = binaryContentMapper.ofUserProfile(user.getId(), request.profileImage());
            binaryContentRepository.save(userProfile);
        }
        user = userMapper.update(request, user);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public void delete(UUID id) {
        User user = userRepository.find(id).orElseThrow(() ->
                new IllegalArgumentException(USER_NOT_FOUND_BY_ID.formatted(id)));
        binaryContentRepository.findUserProfile(user.getId())
                .ifPresent(userProfile -> binaryContentRepository.delete(userProfile.getId()));
        UserStatus userStatus = userStatusRepository.findByUserId(id).orElseThrow(() ->
                new IllegalStateException(USER_STATUS_NOT_FOUND_BY_USER_ID.formatted(user.getId())));
        userStatusRepository.delete(userStatus.getId());
        userRepository.delete(user.getId());
    }
}
