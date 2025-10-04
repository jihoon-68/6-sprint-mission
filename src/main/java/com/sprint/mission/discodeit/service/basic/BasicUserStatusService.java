package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatus.CreateUserStatusDTO;
import com.sprint.mission.discodeit.dto.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatus create(CreateUserStatusDTO createUserStatusDTO) {
        User user = userRepository.findById(createUserStatusDTO.userId()).orElseThrow(
                ()-> new NoSuchElementException("user not found"));

        if (userStatusRepository.findByUserId(createUserStatusDTO.userId()).isPresent()) {
            throw new DuplicateFormatFlagsException("Duplicate UserStatus");
        }
        return new UserStatus(user);
    }

    @Override
    public UserStatus findById(UUID id) {
        return userStatusRepository.findById(id).orElseThrow(()-> new NoSuchElementException("user not found"));
    }

    @Override
    public List<UserStatus> findAll() {
        return List.copyOf(userStatusRepository.findAll());
    }

    @Override
    public void update(UpdateUserStatusDTO  updateUserStatusDTO) {
        UserStatus userStatus = userStatusRepository.findById(updateUserStatusDTO.userId())
                .orElseThrow(()-> new NoSuchElementException("user not found"));
        userStatus.update(updateUserStatusDTO.lastActiveAt());
        userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatus updateByUserId(UUID userId, UserStatusUpdateRequest userStatusUpdateRequest) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(()-> new NoSuchElementException("user not found"));

        userStatus.isConnecting(userStatusUpdateRequest.newLastActiveAt());
        return userStatusRepository.save(userStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.deleteById(id);
    }
}
