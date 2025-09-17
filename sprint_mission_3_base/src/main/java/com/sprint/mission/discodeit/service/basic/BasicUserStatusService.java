package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.DTO.Status.CreateUserStatusRequest;
import com.sprint.mission.discodeit.DTO.Status.UpdateUserStatusRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatus create(CreateUserStatusRequest request) {
        User user = userRepository.findById(request.userId()).orElse(null);

        if(user == null) {
            throw new NoSuchElementException("User with id " + request.userId() + " not found");
        }

        if(userStatusRepository.findByUserId(request.userId()) != null) {
            throw new IllegalArgumentException("UserStatus already exists");
        }

        UserStatus userStatus = new UserStatus(request.userId());
        userStatusRepository.save(userStatus);
        return userStatus;
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
                .map(x->x.getValue())
                .toList();
    }

    @Override
    public UserStatus update(UpdateUserStatusRequest request) {
        UserStatus userStatus = userStatusRepository.find(request.id())
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found with id " + request.id()));

        userStatus.update();
        return userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatus updateByUserId(UUID userId) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found with userId " + userId));

        userStatus.update();
        return userStatusRepository.save(userStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.delete(id);
    }
}
