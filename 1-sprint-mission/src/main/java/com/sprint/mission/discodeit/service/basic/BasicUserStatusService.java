package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTOs.UserStatus.CreateUserStatusDTO;
import com.sprint.mission.discodeit.DTOs.UserStatus.UpdateUserStatusDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    public BasicUserStatusService(UserStatusRepository userStatusRepository, UserRepository userRepository) {
        this.userStatusRepository = userStatusRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserStatus create(CreateUserStatusDTO userStatusDto) {
        User user = userRepository.findById(userStatusDto.userId()).orElse(null);
        if(user == null)
            throw new NoSuchElementException("User not found");

        if(userStatusRepository.findByUserId(user.getId()) != null)
            throw new IllegalStateException("UserStatus already exists");

        UserStatus status = new UserStatus(userStatusDto.userId());
        userStatusRepository.save(status);
        return status;
    }

    @Override
    public UserStatus find(UUID id) {
        return userStatusRepository.find(id).orElse(null);
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatusRepository
                .findAll()
                .entrySet()
                .stream()
                .map(x -> x.getValue()).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public UserStatus update(UpdateUserStatusDTO updateUserStatusDto) {
        UserStatus status = userStatusRepository
                .find(updateUserStatusDto.id())
                .orElseThrow(NoSuchElementException::new);

        status.update();
        return userStatusRepository.save(status);
    }

    @Override
    public UserStatus updateByUserId(UUID userId) {
        UserStatus status = userStatusRepository
                .findByUserId(userId)
                .orElseThrow(NoSuchElementException::new);

        status.update();
        return userStatusRepository.save(status);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.delete(id);
    }
}
