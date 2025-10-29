package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.UserStatusDto;
import com.sprint.mission.discodeit.dto.request.UserStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BasicUserStatusService implements UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final UserStatusMapper userStatusMapper;

    @Override
    @Transactional
    public UserStatusDto create(UserStatusCreateRequest request) {
        UUID userId = request.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not exist"));
        if (userStatusRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("UserStatus for User id " + userId + " already exists");
        }
        Instant lastActiveAt = Instant.now();
        UserStatus userStatus = new UserStatus(user, lastActiveAt);
        UserStatus createdEntity = userStatusRepository.save(userStatus);
        return userStatusMapper.toDto(createdEntity);
    }

    @Override
    public UserStatusDto find(UUID userStatusId) {
        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(
                        () -> new EntityNotFoundException("UserStatus with id " + userStatusId + " not found"));
        return userStatusMapper.toDto(userStatus);
    }

    @Override
    public List<UserStatusDto> findAll() {
        return userStatusRepository.findAll().stream()
                .map(userStatusMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserStatusDto update(UUID userStatusId, UserStatusUpdateRequest request) {
        Instant newLastActiveAt = Instant.now();
        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(
                        () -> new EntityNotFoundException("UserStatus with id " + userStatusId + " not found"));
        userStatus.update(newLastActiveAt, request.status());
        return userStatusMapper.toDto(userStatus);
    }

    @Override
    @Transactional
    public UserStatusDto updateByUserId(UUID userId, UserStatusUpdateRequest request) {
        Instant newLastActiveAt = Instant.now();
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("UserStatus with userId " + userId + " not found"));
        userStatus.update(newLastActiveAt, request.status());
        return userStatusMapper.toDto(userStatus);
    }

    @Override
    public UserStatusDto findByUserId(UUID userId) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("UserStatus with userId " + userId + " not found"));
        return userStatusMapper.toDto(userStatus);
    }

    @Override
    @Transactional
    public void delete(UUID userStatusId) {
        UserStatus userStatus = userStatusRepository.findById(userStatusId)
                .orElseThrow(() -> new EntityNotFoundException("UserStatus with id " + userStatusId + " not found"));
        userStatusRepository.delete(userStatus);
    }
}
