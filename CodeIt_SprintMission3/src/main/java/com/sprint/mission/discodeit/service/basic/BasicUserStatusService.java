package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateUserStatusRequest;
import com.sprint.mission.discodeit.dto.UserStatusResponse;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatusResponse create(CreateUserStatusRequest request) {
        if (!userRepository.existsById(request.userId())) {
            throw new NoSuchElementException("User not found with id: " + request.userId());
        }
        if (userStatusRepository.findByUserId(request.userId()).isPresent()) {
            throw new IllegalArgumentException("UserStatus already exists for the given user");
        }

        UserStatus userStatus = new UserStatus(request.userId());
        userStatusRepository.save(userStatus);
        return UserStatusResponse.of(userStatus);
    }

    @Override
    public UserStatusResponse find(UUID userStatusId) {
        return userStatusRepository.findById(userStatusId)
                .map(UserStatusResponse::of)
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found with id: " + userStatusId));
    }

    @Override
    public List<UserStatusResponse> findAll() {
        return userStatusRepository.findAll()
                .stream()
                .map(UserStatusResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public UserStatusResponse update(UUID userStatusId) {
        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found with id: " + userStatusId));
        userStatus.update();
        userStatusRepository.save(userStatus);
        return UserStatusResponse.of(userStatus);
    }

    @Override
    public UserStatusResponse updateByUserId(UUID userId) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found for user id: " + userId));
        userStatus.update();
        userStatusRepository.save(userStatus);
        return UserStatusResponse.of(userStatus);
    }

    @Override
    public void delete(UUID userStatusId) {
        if (!userStatusRepository.existsById(userStatusId)) {
            throw new NoSuchElementException("UserStatus not found with id: " + userStatusId);
        }
        userStatusRepository.deleteById(userStatusId);
    }
}
