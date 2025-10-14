package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatus.CreateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UpdateUserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusDto;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.mapper.UserStatusMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserStatusMapper userStatusMapper;
    private final UserRepository userRepository;

    @Override
    public UserStatusDto create(CreateUserStatusDto createUserStatusDTO) {
        User user = userRepository.findById(createUserStatusDTO.userId()).orElseThrow(
                () -> new NoSuchElementException("user not found"));

        if (userStatusRepository.findByUserId(createUserStatusDTO.userId()).isPresent()) {
            throw new DuplicateFormatFlagsException("Duplicate UserStatus");
        }
        UserStatus userStatus = new UserStatus(user);
        return userStatusMapper.toDto(userStatusRepository.save(userStatus));
    }

    @Override
    public List<UserStatusDto> findAll() {
        return userStatusRepository.findAll().stream()
                .map(userStatusMapper::toDto)
                .toList();
    }

    @Override
    public void update(UpdateUserStatusDto updateUserStatusDTO) {
        UserStatus userStatus = userStatusRepository.findById(updateUserStatusDTO.userId())
                .orElseThrow(() -> new NoSuchElementException("user not found"));
        userStatus.update(updateUserStatusDTO.lastActiveAt());
        userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatusDto updateByUserId(UUID userId, UserStatusUpdateRequest userStatusUpdateRequest) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("user not found"));

        userStatus.isConnecting(userStatusUpdateRequest.newLastActiveAt());
        userStatusRepository.save(userStatus);
        return userStatusMapper.toDto(userStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.deleteById(id);
    }
}
