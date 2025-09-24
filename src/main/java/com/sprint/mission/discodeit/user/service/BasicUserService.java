package com.sprint.mission.discodeit.user.service;

import com.sprint.mission.discodeit.binarycontent.BinaryContentDto;
import com.sprint.mission.discodeit.binarycontent.domain.BinaryContent.OwnerType;
import com.sprint.mission.discodeit.binarycontent.service.BinaryContentService;
import com.sprint.mission.discodeit.user.UserDto.Request;
import com.sprint.mission.discodeit.user.UserDto.Response;
import com.sprint.mission.discodeit.user.UserDto.ResponseWithLastActivatedAt;
import com.sprint.mission.discodeit.user.UserDto.ResponseWithOnline;
import com.sprint.mission.discodeit.user.UserMapper;
import com.sprint.mission.discodeit.user.domain.User;
import com.sprint.mission.discodeit.user.domain.UserCredentials;
import com.sprint.mission.discodeit.user.domain.UserStatus;
import com.sprint.mission.discodeit.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.InstantSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class BasicUserService implements UserService {

    private final BinaryContentService binaryContentService;
    private final UserRepository userRepository;
    private final InstantSource instantSource;

    public BasicUserService(
            BinaryContentService binaryContentService,
            UserRepository userRepository,
            InstantSource instantSource
    ) {
        this.binaryContentService = binaryContentService;
        this.userRepository = userRepository;
        this.instantSource = instantSource;
    }

    @Override
    public Response createUser(Request request) {
        User user = UserMapper.from(request);
        user = userRepository.save(user);
        if (!request.profileImageBase64().isEmpty()) {
            var userProfileRequest = new BinaryContentDto.Request(
                    OwnerType.USER_PROFILE,
                    user.getId(),
                    request.profileImageBase64()
            );
            binaryContentService.createBinaryContent(userProfileRequest);
        }
        return UserMapper.toResponse(user);
    }

    @Override
    public ResponseWithOnline getUserById(UUID id) {
        User user = userRepository.findById(id);
        UUID userProfileId = binaryContentService.getUserProfileByUserId(user.getId())
                .map(BinaryContentDto.Response::id)
                .orElse(null);
        Instant now = instantSource.instant();
        boolean online = user.getUserStatus().isOnline(now);
        return UserMapper.toResponse(user, userProfileId, online);
    }

    @Override
    public ResponseWithLastActivatedAt getUserByNicknameAndPassword(String nickname, String password) {
        User user = userRepository.findByNicknameAndPassword(nickname, password);
        return UserMapper.toResponseWithLastActivatedAt(user);
    }

    @Override
    public Set<ResponseWithOnline> getUsers() {
        Iterable<User> users = userRepository.findAll();
        Instant now = instantSource.instant();
        Set<ResponseWithOnline> userResponses = new HashSet<>();
        for (User user : users) {
            UUID userProfileId = binaryContentService.getUserProfileByUserId(user.getId())
                    .map(BinaryContentDto.Response::id)
                    .orElse(null);
            final boolean online = user.getUserStatus().isOnline(now);
            userResponses.add(UserMapper.toResponse(user, userProfileId, online));
        }
        return Collections.unmodifiableSet(userResponses);
    }

    @Override
    public Response updateUserById(UUID id, Request request) {
        User user = userRepository.findById(id);
        binaryContentService.getUserProfileByUserId(user.getId())
                .ifPresent(response -> binaryContentService.deleteBinaryContentById(response.id()));
        if (!request.profileImageBase64().isEmpty()) {
            var userProfileRequest = new BinaryContentDto.Request(
                    OwnerType.USER_PROFILE,
                    user.getId(),
                    request.profileImageBase64()
            );
            binaryContentService.createBinaryContent(userProfileRequest);
        }
        UserCredentials userCredentials = new UserCredentials(request.nickname(), request.password());
        user = user.withUserCredentials(userCredentials).with(request.name(), request.mail());
        user = userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    @Override
    public ResponseWithLastActivatedAt updateUserById(UUID id, Instant lastActivatedAt) {
        User user = userRepository.findById(id);
        user = user.withUserStatus(new UserStatus(lastActivatedAt));
        user = userRepository.save(user);
        return UserMapper.toResponseWithLastActivatedAt(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id);
        binaryContentService.getUserProfileByUserId(user.getId())
                .ifPresent(response -> binaryContentService.deleteBinaryContentById(response.id()));
        userRepository.deleteById(user.getId());
    }
}
